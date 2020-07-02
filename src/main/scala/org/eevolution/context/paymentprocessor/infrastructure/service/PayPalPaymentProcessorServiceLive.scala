/**
  * Copyright (C) 2003-2020, e-Evolution Consultants S.A. , http://www.e-evolution.com
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * Email: victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
  * Created by victor.perez@e-evolution.com , www.e-evolution.com
  **/


package org.eevolution.context.paymentprocessor.infrastructure.service

import org.adempiere.exceptions.AdempiereException
import org.compiere.util.{Env, Msg}
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.service.{BankAccountService, BankService, CurrencyService, PartnerBankAccountService, PartnerService, PaymentProcessorService, PaymentService, SecurityService, UserService}
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PaymentProcessor}
import paypal.payflow.{CardTender, CreditCard, Currency, PayflowConnectionData, PayflowConstants, PayflowUtility, SDKProperties, SaleTransaction, TransactionResponse, UserInfo}
import zio.{IO, RIO, ZIO}

case class PayPalPaymentProcessorServiceLive(paymentProcessorRepository: PaymentProcessorRepository.Service,
                                             securityService: SecurityService.Service,
                                             paymentService: PaymentService.Service,
                                             bankAccountService: BankAccountService.Service,
                                             bankService: BankService.Service,
                                             partnerService: PartnerService.Service,
                                             partnerBankAccountService: PartnerBankAccountService.Service,
                                             currencyService: CurrencyService.Service,
                                             userService: UserService.Service
                                            ) extends PaymentProcessorService.Service {

  override def getById(id: Id): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.getById(id)

  override def get(bankAccountId: Id, name: String): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.get(bankAccountId, name)

  def transtale(translationData: String) = ZIO.effectTotal(Msg.parseTranslation(Env.getCtx, translationData))

  def processing(paymentId: Id): RIO[Any, Option[String]] = for {
    //Get the account bank for Stripe Bank into ERP based on Bank Account id save in payment
    maybePayment <- paymentService.getById(paymentId)
    maybeBankAccount <- bankAccountService.getById(maybePayment.get.bankAccountId)
    maybePaymentProcessor <- paymentProcessorRepository.get(maybeBankAccount.get.bankAccountId, "PayPal")
    maybePartner <- partnerService.getById(maybePayment.get.partnerId)
    //Validate Account Email Payment with Partner Value for Stripe Should be same that Business Partner Value
    partner <- if (maybePartner.get.value.equals(maybePayment.get.accountEMail)) RIO.succeed(maybePartner.get) else RIO.fail(new AdempiereException("@C_BPartner_ID@ @Error@ @A_Email@"))
    //Validate Account Email Payment with Partner Value for Stripe Should be same that Business Partner Value
    payment <- if (partner.value.equals(maybePayment.get.accountEMail)) RIO.succeed(maybePayment.get) else RIO.fail(new AdempiereException("@C_Payment_ID@ @Error@ @A_Email@"))
    paymentProcessor <- maybePaymentProcessor match {
      case Some(paymentProcessor) => RIO.succeed(paymentProcessor)
      case None => RIO.fail(new AdempiereException("@C_PaymentProcessor_ID@ @NotFound@"))
    }
    (user, connection) <- ZIO.effectAsync[Any, Throwable, (UserInfo, PayflowConnectionData)] {
      callback => {
        //PayPal connection
        SDKProperties.setHostAddress(paymentProcessor.hostAddress)
        SDKProperties.setHostPort(paymentProcessor.hostPort)
        SDKProperties.setTimeOut(45)
        // Logging is by default off. To turn logging on uncomment the following lines:
        SDKProperties.setLogFileName("payflow_java.log")
        SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_DEBUG)
        SDKProperties.setMaxLogFileSize(100000)
        SDKProperties.setStackTraceOn(true)
        val user = new UserInfo(
          paymentProcessor.userId,
          paymentProcessor.vendorId,
          paymentProcessor.partnerId,
          paymentProcessor.password)
        val connection = new PayflowConnectionData
        val connectionData: (UserInfo, PayflowConnectionData) = (user, connection)
        callback(ZIO.succeed(connectionData))
      }
    }
    //Get the currency for this payment
    maybeCurrency <- currencyService.getById(payment.currencyId)
    //Create an Invoice with Payment Amount
    invoice <- RIO.effectTotal {
      val paymentAmount = new Currency(payment.paymentAmount.get.toDouble, maybeCurrency.get.isoCode)
      val invoice = new paypal.payflow.Invoice()
      invoice.setAmt(paymentAmount)
      invoice.setPoNum(payment.poNumber)
      invoice.setInvNum(payment.reference)
      invoice
    }
    //  Create a new Tender
    card <- IO.effectTotal {
      val expirationDate = s"${payment.creditCardExpMM}${payment.creditCardExpYY}"
      val creditCard = new CreditCard(payment.creditCardNumber, expirationDate)
      creditCard.setName(payment.accountName)
      creditCard.setCvv2(payment.creditCardVerificationCode);
      val card = new CardTender(creditCard)
      card
    }
    //Create Transaction
    transactionResponse <- IO.effectAsync[Throwable, TransactionResponse] {
      callback => {
        val salesTransaction = new SaleTransaction(user, connection, invoice, card, PayflowUtility.getRequestId())
        val response = salesTransaction.submitTransaction
        val transactionResponse = response.getTransactionResponse
        callback(ZIO.succeed(transactionResponse))
      }
    }
    // Save the Result into the Payment
    payment <- IO.effectTotal {
      val updatePayment = payment.copy(
        isApproved = true,
        onlineProcessing = "Y",
        authorizationCode = transactionResponse.getAuthCode,
        authorizationCodeDC = transactionResponse.getProcCVV2,
        addressVerified = transactionResponse.getAvsAddr,
        zipVerified = transactionResponse.getAvsZip,
        cvvMatch = "Y".equals(transactionResponse.getCvv2Match),
        info = transactionResponse.toString,
        reference = transactionResponse.getPnref,
        referenceDC = transactionResponse.getCustRef,
        responseMessage = transactionResponse.getRespMsg,
        result = transactionResponse.getResult.toString)
      updatePayment
    }
    paymentUpdated <- paymentService.save(payment)
    responseMessage <- ZIO.succeed(Option(paymentUpdated.get.responseMessage))
  } yield responseMessage
}