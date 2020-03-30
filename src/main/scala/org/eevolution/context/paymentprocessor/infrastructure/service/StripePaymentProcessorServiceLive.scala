package org.eevolution.context.paymentprocessor.infrastructure.service

import com.stripe.Stripe
import com.stripe.model.{Charge, Customer, Source, Token}
import org.adempiere.exceptions.AdempiereException
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.service.{BankAccountService, BankService, CurrencyService, PartnerBankAccountService, PartnerService, PaymentProcessorService, PaymentService, UserService}
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Payment, PaymentProcessor}
import zio.{RIO, ZIO}

import scala.util.Try
import scala.jdk.CollectionConverters._



case class StripePaymentProcessorServiceLive(paymentProcessorRepository : PaymentProcessorRepository.Service ,
                                             paymentService: PaymentService.Service,
                                             bankAccountService: BankAccountService.Service,
                                             bankService: BankService.Service,
                                             partnerService : PartnerService.Service,
                                             partnerBankAccountService : PartnerBankAccountService.Service,
                                             currencyService: CurrencyService.Service,
                                             userService: UserService.Service) extends  PaymentProcessorService.Service {


  override def getById(id: Id): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.getById(id)

  override def get(bankAccountId: Id, name: String): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.get(bankAccountId, name)

  def processing(paymentId: Id): RIO[Any, Option[String]] = for {
    maybePayment <- paymentService.getById(paymentId)
    maybeBankAccount <- bankAccountService.getById(maybePayment.get.bankAccountId)
    maybePaymentProcessor <- get(maybeBankAccount.get.bankAccountId, "Stripe")
    _ <- RIO.effectTotal(Stripe.apiKey = maybePaymentProcessor.get.password)
    // get the partner based on partner id register in payment created
    partner <- partnerService.getById(maybePayment.get.partnerId)
    // get or create a Stripe Customer based on the email , so the partner.value is the unique key and is based on email
    customer <- RIO.fromTry(
      Try {
        // find the Stripe customer based on email if exit then return if not create a new
        val queryOptions = new java.util.HashMap[String, Object]()
        queryOptions.put("email", partner.get.value)
        val customer: Customer = Customer.list(queryOptions).getData.asScala.headOption match {
          case Some(customer) => customer
          case None =>
            val customerParameters = new java.util.HashMap[String, Object]()
            customerParameters.put("email", partner.get.value)
            customerParameters.put("name", partner.get.name)
            val customer = Customer.create(customerParameters)
            customer
        }
        customer
      })
    //Try get a contact with "Stripe" User Name for this partner suing the email and
    //Stripe Customer id save as User password If not exist then create a new user
    maybeStripeUser <- userService.getUser(
      partner.get.partnerId,
      "Stripe",
      partner.get.value,
      customer.getId)
      // Create new User link this Partner
      .orElse(userService.create(partner.get, "Stripe", partner.get.value, customer.getId))
    //Persistence stripe User
    _ <- userService.save(maybeStripeUser.get)
    //Try get the credit card as partner bank account for this partner the Stripe Bank id , user id ,
    //email and payment account name , if partner bank account for this credit card not exist
    //then is new partner bank account created
    maybePartnerBankAccount <- partnerBankAccountService.getById(
      partner.get.partnerId,
      maybeBankAccount.get.bankId,
      maybeStripeUser.get.userId,
      maybePayment.get.accountEMail,
      null,
      maybePayment.get.accountName)
      // Create a default partner bank account for this credit card
      .orElse(partnerBankAccountService.create(
        partner.get.partnerId,
        maybeBankAccount.get.bankId,
        maybeStripeUser.get.userId,
        maybePayment.get.accountEMail,
        maybePayment.get.accountNo,
        maybePayment.get.accountName,
        maybePayment.get.creditCardType,
        maybePayment.get.creditCardNumber,
        maybePayment.get.creditCardExpMM,
        maybePayment.get.creditCardExpYY,
        maybePayment.get.creditCardVerificationCode))
    // Try get Stripe Source or Generate a Credit Card Token
    source <- RIO.fromTry(
      Try {
        // If account exist the return this source to process payment , if not then is new token created
        Option(maybePartnerBankAccount.get.accountNo) match {
          case Some(accountNo) => Source.retrieve(accountNo) //return default source payment
          case None =>
            //Generate new Stripe source based on credit card info
            val cardParameters = new java.util.HashMap[String, Object]()
            val creditCardExpMM = maybePayment.get.creditCardExpMM
            val creditCardExpYY = maybePayment.get.creditCardExpYY
            cardParameters.put("number", maybePayment.get.creditCardNumber)
            cardParameters.put("exp_month", creditCardExpMM.toString)
            cardParameters.put("exp_year", creditCardExpYY.toString)
            cardParameters.put("cvc", maybePayment.get.creditCardVerificationCode)
            //Create Stripe token
            val token = Token.create(cardParameters)
            //The new token is setting as accountNo
            maybePartnerBankAccount match {
              case Some(partnerBankAccount) =>
                partnerBankAccount.copy(accountNo = token.getId)
                partnerBankAccountService.save(partnerBankAccount)
                partnerBankAccount
              case None => ZIO.fail(new AdempiereException("@Error@"))
            }
            //Get the Stripe Source for this token
            val source = Source.retrieve(token.getId)
            source
        }
      })
    //Get the currency for this payment
    currency <- currencyService.getById(maybePayment.get.currencyId)
    //Generate a new charge for payment amount and return the Stripe Charge status
    charge <- RIO.fromTry(
      Try {
        val chargeParams = new java.util.HashMap[String, Object]()
        chargeParams.put("amount", maybePayment.get.paymentAmount * 100)
        chargeParams.put("currency", currency.get.isoCode)
        chargeParams.put("source", source.getId)
        chargeParams.put("description", maybePayment.get.description)
        val charge = Charge.create(chargeParams)
        charge
      })
    // Save result
    responseMessage <- RIO.fromTry(
      Try {
        maybePayment match {
          case Some(payment) =>
            payment.copy(authorizationCode = charge.getAuthorizationCode, info = charge.getDescription, reference = charge.getId, referenceDC = charge.getPaymentIntent, responseMessage = charge.getFailureMessage, result = charge.getStatus)
            paymentService.save(payment)
            payment
          case None => ZIO.fail("@Error@")
        }
       charge.getStatus
      }
    )
  } yield responseMessage
}
