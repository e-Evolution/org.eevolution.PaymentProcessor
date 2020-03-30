package org.eevolution.context.paymentprocessor.infrastructure.service

import org.adempiere.exceptions
import org.adempiere.exceptions.AdempiereException
import org.compiere.util.{Env, Msg}
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.service._
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Payment, PaymentProcessor}
import paypal.payflow._
import zio.{RIO, ZIO}

import scala.util.Try

case class PayPalPaymentProcessorServiceLive(paymentProcessorRepository : PaymentProcessorRepository.Service ,
                                             paymentService: PaymentService.Service,
                                             bankAccountService: BankAccountService.Service,
                                             bankService: BankService.Service,
                                             partnerService : PartnerService.Service,
                                             partnerBankAccountService : PartnerBankAccountService.Service,
                                             currencyService: CurrencyService.Service,
                                             userService: UserService.Service
                                            ) extends  PaymentProcessorService.Service {

  override def getById(id: Id): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.getById(id)

  override def get(bankAccountId: Id, name: String): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.get(bankAccountId, name)

  def transtale(translationData : String)  = ZIO.effectTotal(Msg.parseTranslation(Env.getCtx , translationData))

  def processing(paymentId: Id): RIO[Any, Option[String]] = for {
    //Get the account bank for Stripe Bank into ERP based on Bank Account id save in payment
    maybePayment <- paymentService.getById(paymentId)
    maybeBankAccount <- bankAccountService.getById(maybePayment.get.bankAccountId)
    maybePaymentProcessor <- paymentProcessorRepository.get(maybeBankAccount.get.bankAccountId, "PayPal")
    (user, connection) <- RIO.effectTotal {
      //PayPal connection
      SDKProperties.setHostAddress(maybePaymentProcessor.get.hostAddress)
      SDKProperties.setHostPort(maybePaymentProcessor.get.hostPort)
      SDKProperties.setTimeOut(45)
      // Logging is by default off. To turn logging on uncomment the following lines:
      SDKProperties.setLogFileName("payflow_java.log")
      SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_DEBUG)
      SDKProperties.setMaxLogFileSize(100000)
      SDKProperties.setStackTraceOn(true)
      val user = new UserInfo(
        maybePaymentProcessor.get.userId,
        maybePaymentProcessor.get.vendorId,
        maybePaymentProcessor.get.partnerId,
        maybePaymentProcessor.get.password)
      val connection = new PayflowConnectionData
      (user, connection)
    }
    // get the partner based on partner id register in payment created
    maybePartner <- partnerService.getById(maybePayment.get.partnerId)
    //Get the currency for this payment
    maybeCurrency <- currencyService.getById(maybePayment.get.currencyId)
    //Create an Invoice with Payment Amount
    invoice <- RIO.effectTotal{
      val paymentAmount = new Currency(maybePayment.get.paymentAmount.toDouble, maybeCurrency.get.isoCode)
      val invoice = new Invoice()
      invoice.setAmt(paymentAmount)
      invoice.setPoNum(maybePayment.get.poNumber)
      invoice.setInvNum(maybePayment.get.reference)
      invoice
    }
    //  Create a new Tender
    card <- RIO.fromTry(
      Try {
        val expirationDate = s"${maybePayment.get.creditCardExpMM}${maybePayment.get.creditCardExpYY}"
        val creditCard = new CreditCard(maybePayment.get.creditCardNumber, expirationDate);
        creditCard.setCvv2(maybePayment.get.creditCardVerificationCode);
        val card = new CardTender(creditCard);
        card
      }
    )
    //Create Transaction
    transactionResponse <- RIO.fromTry(
      Try {
        val salesTransaction = new SaleTransaction(user, connection, invoice, card, PayflowUtility.getRequestId())
        val response = salesTransaction.submitTransaction
        val transactionResponse = response.getTransactionResponse
        transactionResponse
      }
    )
    // Save the Result into the Payment
    responseMessage <- RIO.fromTry(
      Try {
        maybePayment match {
          case Some(payment) => payment.copy(authorizationCode = transactionResponse.getAuthCode, authorizationCodeDC = transactionResponse.getProcCVV2, addressVerified = transactionResponse.getAvsAddr, zipVerified = transactionResponse.getAvsZip, cvvMatch = "Y".equals(transactionResponse.getCvv2Match), info = transactionResponse.toString, reference = transactionResponse.getPnref, referenceDC = transactionResponse.getCustRef, responseMessage = transactionResponse.getRespMsg, result = transactionResponse.getResult.toString)
            paymentService.save(payment)
          case None => ZIO.fail(new AdempiereException("@C_Payment_ID@ @NotFound@"))
        }
        Option(transactionResponse.getRespMsg)
      })
  } yield responseMessage
}