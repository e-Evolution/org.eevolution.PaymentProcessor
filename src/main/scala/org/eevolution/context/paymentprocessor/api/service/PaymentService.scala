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


package org.eevolution.context.paymentprocessor.api.service

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{BankAccount, Currency, DocumentType, Id, List, Partner, Payment}
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository
import zio.ZIO

/**
  * API Trait Domain Payment Service
  */
trait PaymentService {
  def paymentService: PaymentService.Service
}

/**
  * API Singleton Object Domain Payment Service
  */
object PaymentService {

  type PaymentServiceEnvironment = PaymentRepository with ContextEnvironment

  trait Service {
    def getById(id: Id): ZIO[PaymentServiceEnvironment, Throwable, Payment]

    def save (payment: Payment): ZIO[PaymentServiceEnvironment, Throwable, Payment]

    def createPayment(documentType: DocumentType,
                      description: String,
                      bankAccount: BankAccount,
                      partner: Partner,
                      currency: Currency): ZIO[PaymentServiceEnvironment, Any, Payment]

    def createPaymentWithCreditCard(documentType: DocumentType,
                                    description: String,
                                    bankAccount: BankAccount,
                                    partner: Partner,
                                    currency: Currency,
                                    creditCardType: List,
                                    creditCard: String,
                                    creditCardNumber: String,
                                    creditCardExpMM: Int,
                                    creditCardExpYY: Int,
                                    verificationCode: String): ZIO[PaymentServiceEnvironment, Any, Payment]
  }

  trait Live extends PaymentService

  object Live extends PaymentService.Live {
    def paymentService: PaymentService.Service = new Service {
      override def getById(id: Id): ZIO[PaymentServiceEnvironment, Throwable, Payment] = ???

      override def createPayment(documentType: DocumentType,
                                 description: String,
                                 bankAccount: BankAccount,
                                 partner: Partner,
                                 currency: Currency): ZIO[PaymentServiceEnvironment, Any, Payment] = ???

      override def createPaymentWithCreditCard(documentType: DocumentType,
                                               description: String,
                                               bankAccount: BankAccount,
                                               partner: Partner,
                                               currency: Currency,
                                               creditCardType: List,
                                               creditCardNumber: String,
                                               creditCard: String,
                                               creditCardExpMM: Id,
                                               creditCardExpYY: Id,
                                               verificationCode: String): ZIO[PaymentServiceEnvironment, Any, Payment] = ???

      override def save(payment: Payment): ZIO[PaymentServiceEnvironment, Throwable, Payment] = ???
    }
  }

}