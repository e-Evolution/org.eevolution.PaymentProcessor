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

import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.service.BankAccountService
import org.eevolution.context.paymentprocessor.api.service.BankAccountService.BankAccountService
import org.eevolution.context.paymentprocessor.api.service.BankService
import org.eevolution.context.paymentprocessor.api.service.BankService.BankService
import org.eevolution.context.paymentprocessor.api.service.CurrencyService
import org.eevolution.context.paymentprocessor.api.service.CurrencyService.CurrencyService
import org.eevolution.context.paymentprocessor.api.service.PartnerBankAccountService.PartnerBankAccountService
import org.eevolution.context.paymentprocessor.api.service.PartnerService
import org.eevolution.context.paymentprocessor.api.service.PartnerService.PartnerService
import org.eevolution.context.paymentprocessor.api.service.PaymentService
import org.eevolution.context.paymentprocessor.api.service.PaymentService.PaymentService
import org.eevolution.context.paymentprocessor.api.service.UserService
import org.eevolution.context.paymentprocessor.api.service.UserService.UserService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount, Payment, PaymentProcessor}
import org.eevolution.context.paymentprocessor.infrastructure.service.PayPalPaymentProcessorServiceLive
import org.eevolution.context.paymentprocessor.infrastructure.service.StripePaymentProcessorServiceLive
import zio.{Has, RIO, ZLayer}

import scala.util.Try

/**
  * Standard Implementation for Domain Payment Service
  */
object PaymentProcessorService {


  type PaymentProcessorService = Has[Service]

  trait Service {
    def getById(id: Id): RIO[Any, Option[PaymentProcessor]]

    def get(bankAccountId: Id, name: String): RIO[Any, Option[PaymentProcessor]]

    def processing(paymentId: Id): RIO[Any, Option[String]]
  }

  def live : ZLayer[PaymentProcessorRepository , Throwable, Has[Service]] = ZLayer.fromService[PaymentProcessorRepository.Service, Service]{
    paymentProcessorRepository => {
      new PaymentProcessorService.Service {

        override def getById(id: Id): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.getById(id)

        override def get(bankAccountId: Id, name: String): RIO[Any, Option[PaymentProcessor]] = paymentProcessorRepository.get(bankAccountId, name)

        def processing(paymentId: Id): RIO[Any, Option[String]] = ???
      }
    }

  }

  type LiveDependencies = PaymentProcessorRepository with PaymentService with  BankAccountService  with BankService with  PartnerService with PartnerBankAccountService with CurrencyService with UserService

  def paypal: ZLayer[LiveDependencies, Throwable, Has[Service]] = ZLayer.fromServices[PaymentProcessorRepository.Service , PaymentService.Service,BankAccountService.Service,BankService.Service, PartnerService.Service ,PartnerBankAccountService.Service , CurrencyService.Service ,  UserService.Service, Service] {
    ( paymentProcessorRepository , paymentService ,bankAccountService , bankService , partnerService , partnerBankAccountService , currencyService, userService ) =>
      PayPalPaymentProcessorServiceLive(paymentProcessorRepository , paymentService ,bankAccountService , bankService , partnerService , partnerBankAccountService , currencyService, userService)
  }

  def stripe: ZLayer[LiveDependencies, Nothing, Has[Service]] = ZLayer.fromServices[PaymentProcessorRepository.Service , PaymentService.Service , BankAccountService.Service , BankService.Service, PartnerService.Service , PartnerBankAccountService.Service , CurrencyService.Service ,  UserService.Service, Service] {
    (paymentProcessorRepository , paymentService ,bankAccountService , bankService , partnerService , partnerBankAccountService , currencyService, userService) =>
      StripePaymentProcessorServiceLive(paymentProcessorRepository , paymentService ,bankAccountService , bankService , partnerService , partnerBankAccountService , currencyService, userService)
  }
}