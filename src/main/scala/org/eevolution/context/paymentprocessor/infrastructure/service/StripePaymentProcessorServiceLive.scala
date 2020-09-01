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

import com.stripe.Stripe
import com.stripe.model.{Charge, Customer, PaymentSource, Token}
import org.adempiere.exceptions
import org.adempiere.exceptions.AdempiereException
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.service.{BankAccountService, BankService, CurrencyService, PartnerBankAccountService, PartnerService, PaymentProcessorService, PaymentService, SecurityService, UserService}
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PaymentProcessor}
import zio.{IO, RIO, ZIO}

import scala.jdk.CollectionConverters._


case class StripePaymentProcessorServiceLive(paymentProcessorRepository: PaymentProcessorRepository.Service,
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

  def processing(paymentId: Id): RIO[Any, Option[String]] = for {
    maybePayment <- paymentService.getById(paymentId)
    maybeBankAccount <- bankAccountService.getById(maybePayment.get.bankAccountId)
    maybePaymentProcessor <- get(maybeBankAccount.get.bankAccountId, "Stripe")
    _ <- RIO.effectTotal(Stripe.apiKey = maybePaymentProcessor.get.password)
    maybePartner <- partnerService.getById(maybePayment.get.partnerId)
    //Validate Account Email Payment with Partner Value for Stripe Should be same that Business Partner Value
    partner <- if (maybePartner.get.value.equals(maybePayment.get.accountEMail)) RIO.succeed(maybePartner.get) else RIO.fail(new exceptions.AdempiereException("@C_BPartner_ID@ @Error@ @A_Email@"))
    //Validate Account Email Payment with Partner Value for Stripe Should be same that Business Partner Value
    payment <- if (partner.value.equals(maybePayment.get.accountEMail)) RIO.succeed(maybePayment.get) else RIO.fail(new exceptions.AdempiereException("@C_Payment_ID@ @Error@ @A_Email@"))
    // get or create a Stripe Customer based on the email , so the partner.value is the unique key and is based on email
    customer <- IO.effectAsync[Throwable, Customer] {
      callback => {
        // find the Stripe customer based on email if exit then return if not create a new
        val queryOptions = new java.util.HashMap[String, Object]()
        queryOptions.put("email", partner.value)
        val customer: Customer = Customer.list(queryOptions).getData.asScala.headOption match {
          case Some(customer) => customer
          case None =>
            val customerParameters = new java.util.HashMap[String, Object]()
            customerParameters.put("email", partner.value)
            customerParameters.put("name", partner.name)
            val customer = Customer.create(customerParameters)
            customer
        }
        callback(IO.succeed(customer))
      }
    }
    //Try get a contact with "Stripe" User Name for this partner suing the email and
    //Stripe Customer id save as User password If not exist then create a new user
    maybeUser <- userService.getUser(partner.partnerId, "Stripe", partner.value) // Create new User link this Partner
    //Validate or Create Stripe User Contact
    stripeUser <- maybeUser match {
      case Some(user) =>
        for {
          userSaved <- if (user.password.equals(customer.getId) && Option(user.salt).isEmpty) userService.save(maybeUser.get) else userService.getById(maybeUser.get.userId)
          maybePasswordHash <- securityService.getHash(customer.getId, userSaved.get.salt)
          stripeUser <- if (userSaved.get.password.equals(maybePasswordHash.get)) RIO.succeed(userSaved) else RIO.fail(new AdempiereException("Stripe Customer id is Invalid"))
        } yield stripeUser
      case None =>
        val stripeUser = for {
          newUser <- userService.create(partner, "Stripe", partner.value, customer.getId)
          stripeUser <- userService.save(newUser.get)
        } yield stripeUser
        stripeUser
    }
    //Try get the credit card as partner bank account for this partner the Stripe Bank id , user id ,
    //email and payment account name , if partner bank account for this credit card not exist
    //then is new partner bank account created
    maybePartnerBankAccount <- partnerBankAccountService.getById(partner.partnerId, maybeBankAccount.get.bankId, stripeUser.get.userId, partner.value, payment.accountEMail, payment.creditCardNumber)
    //Validate Credit Card or Create a Business Partner Bank Account
    maybeStripePartnerBankAccount <- maybePartnerBankAccount match {
      case Some(partnerBankAccount) => RIO.succeed(Option(partnerBankAccount))
      case None =>
        // Create a default partner bank account for this credit card
        val partnerBankAccount = partnerBankAccountService.create(partner.partnerId, maybeBankAccount.get.bankId, stripeUser.get.userId, partner.value, payment.accountEMail, payment.creditCardType, payment.creditCardNumber, payment.creditCardExpMM, payment.creditCardExpYY, payment.creditCardVerificationCode)
        partnerBankAccount
    }
    // Try get Stripe Source or Generate a Credit Card Token
    source <- IO.effectAsync[Throwable, PaymentSource] {
      callback => {
        // If account exist the return this source to process payment , if not then is new token created
        maybeStripePartnerBankAccount.foreach(stripePartnerBankAccount => {
          val accountName = stripePartnerBankAccount.accountName
          if (accountName.equals(partner.value)) {
            //Generate new Stripe source based on credit card info
            val cardParameters = new java.util.HashMap[String, Object]()
            val creditCardExpMM = payment.creditCardExpMM
            val creditCardExpYY = payment.creditCardExpYY
            cardParameters.put("number", payment.creditCardNumber)
            cardParameters.put("exp_month", creditCardExpMM.toString)
            cardParameters.put("exp_year", creditCardExpYY.toString)
            cardParameters.put("cvc", payment.creditCardVerificationCode)
            val tokenParameters = new java.util.HashMap[String, Object]()
            tokenParameters.put("card", cardParameters);
            //Create Stripe token
            val token = Token.create(tokenParameters)
            //The new token is setting as accountNo
            val updatePartnerBankAccount = stripePartnerBankAccount.copy(accountName = token.getCard.getId)
            partnerBankAccountService.save(updatePartnerBankAccount)
            //Get the Stripe Source for this token
            val sourceParameters = new java.util.HashMap[String, Object]()
            sourceParameters.put("source", token.getId)
            val source = customer.getSources.create(sourceParameters)
            callback(IO.succeed(source))
          } else {
            callback(IO.succeed(customer.getSources.retrieve(accountName)))
          }
        })
      }
    }
    //Get the currency for this payment
    currency <- currencyService.getById(payment.currencyId)
    //Generate a new charge for payment amount and return the Stripe Charge status
    charge <- IO.effectAsync[Throwable, Charge] {
      callback => {
        val paymentAmount = (payment.paymentAmount.get * 100).intValue
        val chargeParams = new java.util.HashMap[String, Object]()
        chargeParams.put("amount", paymentAmount.toString)
        chargeParams.put("currency", currency.get.isoCode)
        chargeParams.put("customer", customer.getId)
        chargeParams.put("source", source.getId)
        chargeParams.put("description", payment.description)
        val charge = Charge.create(chargeParams)
        callback(IO.succeed(charge))
      }
    }
    payment <- IO.effectTotal {
      val updatePayment = payment.copy(
        isApproved = true,
        onlineProcessing = "Y",
        authorizationCode = charge.getAuthorizationCode,
        info = charge.getDescription,
        reference = charge.getId,
        referenceDC = charge.getPaymentIntent,
        responseMessage = charge.getFailureMessage,
        result = charge.getStatus
      )
      updatePayment
    }
    paymentSaved <- paymentService.save(payment)
    responseMessage <- ZIO.succeed(Option(paymentSaved.get.responseMessage))
  } yield responseMessage
}
