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

import java.util

import com.stripe.Stripe
import com.stripe.model.{Charge, Customer, Source, Token}
import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, Payment, PaymentProcessor}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.service.PaymentProcessorService.{PaymentProcessorServiceEnvironment, Service}
import zio.ZIO

import scala.jdk.CollectionConverters._
import scala.util.Try

object StripePaymentProcessorService {

  trait Live extends api.service.PaymentProcessorService

  object Live extends Live {
    override def paymentProcessorService: api.service.PaymentProcessorService.Service = new Service {
      override def getById(id: Id): ZIO[PaymentProcessorServiceEnvironment, Throwable, PaymentProcessor] =
        ZIO.accessM(_.paymentProcessorRepository.getById(id))

      override def get(bankAccountId: Id, name: String): ZIO[PaymentProcessorServiceEnvironment, Throwable, PaymentProcessor] =
        ZIO.accessM(_.paymentProcessorRepository.get(bankAccountId, name))

      override def processing(paymentProcessor: PaymentProcessor, payment: Payment):
      ZIO[PaymentProcessorServiceEnvironment, Any, String] = ZIO.accessM(environment =>
        for {
          bankAccount <- environment.bankAccountService.getById(payment.bankAccountId)
          paymentProcessor <- get(bankAccount.bankAccountId, "Stripe")
          _ <- ZIO.effectTotal(Stripe.apiKey = paymentProcessor.password)
          // get the partner based on partner id register in payment created
          partner <- environment.partnerService.getById(payment.partnerId)
          // get or create a Stripe Customer based on the email , so the partner.value is the unique key and is based on email
          customer <- ZIO.fromTry(
            Try {
              // find the Stripe customer based on email if exit then return if not create a new
              val queryOptions = new util.HashMap[String, Object]()
              queryOptions.put("email", partner.value)
              val customer: Customer = Customer.list(queryOptions).getData.asScala.headOption match {
                case Some(customer) => customer
                case None =>
                  val customerParameters = new util.HashMap[String, Object]()
                  customerParameters.put("email", partner.value)
                  customerParameters.put("name", partner.name)
                  val customer = Customer.create(customerParameters)
                  customer
              }
              customer
            })
          //Try get a contact with "Stripe" User Name for this partner suing the email and
          //Stripe Customer id save as User password If not exist then create a new user
          stripeUser <- environment.userService.getUser(
            partner.partnerId,
            "Stripe",
            partner.value,
            customer.getId)
            // Create new User link this Partner
            .orElse(environment.userService.create(partner, "Stripe", partner.value, customer.getId))
          //Persistence stripe User
          _ <- environment.userService.save(stripeUser)
          //Try get the credit card as partner bank account for this partner the Stripe Bank id , user id ,
          //email and payment account name , if partner bank account for this credit card not exist
          //then is new partner bank account created
          partnerBankAccount <- environment.partnerBankAccountService.getById(
            partner.partnerId,
            bankAccount.bankId,
            stripeUser.userId,
            payment.accountEMail,
            null,
            payment.accountName)
            // Create a default partner bank account for this credit card
            .orElse(environment.partnerBankAccountService.create(
              partner.partnerId,
              bankAccount.bankId,
              stripeUser.userId,
              payment.accountEMail,
              payment.accountNo,
              payment.accountName,
              payment.creditCardType,
              payment.creditCardNumber,
              payment.creditCardExpMM,
              payment.creditCardExpYY,
              payment.creditCardVerificationCode))
          // Try get Stripe Source or Generate a Credit Card Token
          source <- ZIO.fromTry(
            Try {
              // If account exist the return this source to process payment , if not then is new token created
              Option(partnerBankAccount.accountNo) match {
                case Some(accountNo) => Source.retrieve(accountNo) //return default source payment
                case None =>
                  //Generate new Stripe source based on credit card info
                  val cardParameters = new util.HashMap[String, Object]()
                  val creditCardExpMM = payment.creditCardExpMM
                  val creditCardExpYY = payment.creditCardExpYY
                  cardParameters.put("number", payment.creditCardNumber)
                  cardParameters.put("exp_month", creditCardExpMM.toString)
                  cardParameters.put("exp_year", creditCardExpYY.toString)
                  cardParameters.put("cvc", payment.creditCardVerificationCode)
                  //Create Stripe token
                  val token = Token.create(cardParameters)
                  //The new token is setting as accountNo
                  partnerBankAccount.copy(accountNo = token.getId)
                  environment.partnerBankAccountService.save(partnerBankAccount)
                  //Get the Stripe Source for this token
                  val source = Source.retrieve(token.getId)
                  source
              }
            })
          //Get the currency for this payment
          currency <- environment.currencyService.getById(payment.currencyId)
          //Generate a new charge for payment amount and return the Stripe Charge status
          charge <- ZIO.fromTry(
            Try {
              val chargeParams = new util.HashMap[String, Object]()
              chargeParams.put("amount", payment.paymentAmount * 100)
              chargeParams.put("currency", currency.isoCode)
              chargeParams.put("source", source.getId)
              chargeParams.put("description", payment.description)
              val charge = Charge.create(chargeParams)
              charge
            })
          // Save result
          responseMessage <- ZIO.fromTry(
            Try {
              payment.copy(
                authorizationCode = charge.getAuthorizationCode,
                //authorizationCodeDC = ,
                //addressVerified = charge,
                //zipVerified = charge.,
                //cvvMatch = charge,
                info = charge.getDescription,
                reference = charge.getId,
                referenceDC = charge.getPaymentIntent,
                responseMessage = charge.getFailureMessage,
                result = charge.getStatus
              )
              environment.paymentService.save(payment)
              charge.getStatus
            }
          )
        } yield charge.getStatus
      )
    }
  }

}
