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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, Payment, PaymentProcessor}
import org.eevolution.context.paymentprocessor.api.service.PaymentProcessorService
import org.eevolution.context.paymentprocessor.api.service.PaymentProcessorService.{PaymentProcessorServiceEnvironment, Service}
import paypal.payflow._
import zio.ZIO

import scala.util.Try

object PayPalPaymentProcessorService {

  trait Live extends PaymentProcessorService

  object Live extends Live {
    override def paymentProcessorService: PaymentProcessorService.Service = new Service {
      override def getById(id: Id): ZIO[PaymentProcessorServiceEnvironment, Throwable, PaymentProcessor] =
        ZIO.accessM(_.paymentProcessorRepository.getById(id))

      override def get(bankAccountId: Id, name: String): ZIO[PaymentProcessorServiceEnvironment, Throwable, PaymentProcessor] =
        ZIO.accessM(_.paymentProcessorRepository.get(bankAccountId, name))

      override def processing(paymentProcessor: PaymentProcessor, payment: Payment):
      ZIO[PaymentProcessorServiceEnvironment, Any, String] = ZIO.accessM(environment =>
        for {
          //Get the account bank for Stripe Bank into ERP based on Bank Account id save in payment
          bankAccount <- environment.bankAccountService.getById(payment.bankAccountId)
          paymentProcessor <- get(bankAccount.bankAccountId, "PayPal")
          (user, connection) <- ZIO.effectTotal {
            //PayPal connection
            SDKProperties.setHostAddress(paymentProcessor.hostAddress)
            SDKProperties.setHostPort(paymentProcessor.hostPort)
            SDKProperties.setTimeOut(45)
            // Logging is by default off. To turn logging on uncomment the following lines:
            SDKProperties.setLogFileName("payflow_java.log")
            SDKProperties.setLoggingLevel(PayflowConstants.SEVERITY_DEBUG)
            SDKProperties.setMaxLogFileSize(100000)
            SDKProperties.setStackTraceOn(true)

            val user = new UserInfo(paymentProcessor.userId, paymentProcessor.vendorId, paymentProcessor.partnerId, paymentProcessor.password)
            val connection = new PayflowConnectionData
            (user, connection)
          }
          // get the partner based on partner id register in payment created
          partner <- environment.partnerService.getById(payment.partnerId)
          //Get the currency for this payment
          currency <- environment.currencyService.getById(payment.currencyId)
          invoice <- ZIO.fromTry(
            Try {
              val paymentAmount = new paypal.payflow.Currency(payment.paymentAmount.toDouble, currency.isoCode);
              val invoice = new paypal.payflow.Invoice()
              invoice.setAmt(paymentAmount)
              invoice.setPoNum(payment.poNumber)
              invoice.setInvNum(payment.reference)
              invoice
            }
          )
          //  Create a new Tender
          card <- ZIO.fromTry(
            Try {
              val expirationDate = s"${payment.creditCardExpMM}${payment.creditCardExpYY}"
              val creditCard = new CreditCard(payment.creditCardNumber, expirationDate);
              creditCard.setCvv2(payment.creditCardVerificationCode);
              val card = new CardTender(creditCard);
              card
            }
          )
          //Create Transaction
          transactionResponse <- ZIO.fromTry(
            Try {
              val salesTransaction = new SaleTransaction(user, connection, invoice, card, PayflowUtility.getRequestId())
              val response = salesTransaction.submitTransaction
              val transactionResponse = response.getTransactionResponse
              transactionResponse
            }
          )
          // Save the Result into the Payment
          responseMessage <- ZIO.fromTry(
            Try {
              payment.copy(
                authorizationCode = transactionResponse.getAuthCode,
                authorizationCodeDC = transactionResponse.getProcCVV2,
                addressVerified = transactionResponse.getAvsAddr,
                zipVerified = transactionResponse.getAvsZip,
                cvvMatch = "Y".equals(transactionResponse.getCvv2Match),
                info = transactionResponse.toString,
                reference = transactionResponse.getPnref,
                referenceDC = transactionResponse.getCustRef,
                responseMessage = transactionResponse.getRespMsg,
                result = transactionResponse.getResult.toString
              )
              environment.paymentService.save(payment)
              transactionResponse.getRespMsg
            })
        } yield responseMessage
      )


    }
  }


}
