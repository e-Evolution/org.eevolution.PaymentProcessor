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

import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository
import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository.PaymentRepository
import org.eevolution.context.paymentprocessor.domain.service.PaymentServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{BankAccount, Currency, DocumentType, Id, List, Partner, Payment}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain Payment Service
  */
object PaymentService {


  type PaymentService = Has[Service]

  trait Service {
    def getById(id: Id): RIO[Any, Option[Payment]]

    def save(payment: Payment): RIO[Any, Option[Payment]]

    def createPayment(documentType: DocumentType,
                      description: String,
                      bankAccount: BankAccount,
                      partner: Partner,
                      currency: Currency): RIO[Any, Option[Payment]]

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
                                    verificationCode: String): RIO[Any, Option[Payment]]
  }


  def live: ZLayer[PaymentRepository, Throwable, Has[Service]] = ZLayer.fromService[PaymentRepository.Service, Service] ( paymentRepository => PaymentServiceLive(paymentRepository)) //ZLayer.fromServices[Context.Service, PaymentRepository.Service, Service] { (contextService, paymentRepository) =>PaymentServiceLive(contextService, paymentRepository)}
}
