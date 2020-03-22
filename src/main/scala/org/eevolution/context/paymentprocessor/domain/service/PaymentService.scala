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

package org.eevolution.context.paymentprocessor.domain.service

import java.time.Instant

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{BankAccount, Currency, DocumentType, Id, List, Partner, Payment}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.domain.factory.PaymentBuilder
import org.eevolution.context.paymentprocessor.api.service.PaymentService.{PaymentServiceEnvironment, Service}
import org.eevolution.context.paymentprocessor.domain.valueobject.{PaymentTenderType, PaymentTrxType}
import zio.ZIO

/**
  * Standard Implementation for Domain Payment Service
  */
object PaymentService {

  trait Live extends api.service.PaymentService

  object Live extends Live {
    override def paymentService: api.service.PaymentService.Service = new Service {
      override def getById(id: Id): ZIO[PaymentServiceEnvironment, Throwable, Payment] = ZIO.accessM(_.paymentRepository.getById(id))

      override def createPayment(documentType: DocumentType,
                                 description: String,
                                 bankAccount: BankAccount,
                                 partner: Partner,
                                 currency: Currency): ZIO[PaymentServiceEnvironment, Any, Payment] = ZIO.accessM(
        environment => for {
          context <- environment.execution.getContext
          trx <- environment.execution.getTransaction
          tenant <- environment.execution.getTenant
          organization <- environment.execution.getOrganization
          payment <- PaymentBuilder()
            .withDocumentType(documentType)
            .withPartner(partner)
            .withBankAccount(bankAccount)
            .withDateTrx(Instant.now())
            .withDateAccount(Instant.now())
            .withCurrency(currency)
            .withTenderType(PaymentTenderType.Check.value)
            .withPayAmount(0)
            .build()
        } yield payment
      )

      override def createPaymentWithCreditCard(documentType: DocumentType,
                                               description: String,
                                               bankAccount: BankAccount,
                                               partner: Partner,
                                               currency: Currency,
                                               creditCardType: List,
                                               creditCard: String,
                                               creditCardNumber: String,
                                               creditCardExpMM: Int,
                                               creditCardExpYY: Int,
                                               verificationCode: String): ZIO[PaymentServiceEnvironment, Any, Payment] =
        ZIO.accessM(environment =>
          for {
            context <- environment.execution.getContext
            trx <- environment.execution.getTransaction
            tenant <- environment.execution.getTenant
            organization <- environment.execution.getOrganization
            payment <- PaymentBuilder()
              .withDocumentType(documentType)
              .withDescription(description)
              .withPartner(partner)
              .withBankAccount(bankAccount)
              .withDateTrx(Instant.now())
              .withDateAccount(Instant.now())
              .withCurrency(currency)
              .withTenderType(PaymentTenderType.CreditCard.value)
              .withTrxType(PaymentTrxType.Sales.value)
              .withCreditCardType(creditCardType)
              .withCreditCardNumber(creditCardNumber)
              .withCreditCardExpMM(creditCardExpMM)
              .withCreditCardExpYY(creditCardExpYY)
              .withVerificationCode(verificationCode)
              .withPayAmount(0)
              .build()
          } yield payment
        )

      override def save(payment: Payment): ZIO[PaymentServiceEnvironment, Throwable, Payment] = ???
    }
  }

}
