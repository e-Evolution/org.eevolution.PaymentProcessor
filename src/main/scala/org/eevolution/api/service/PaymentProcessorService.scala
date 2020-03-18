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


package org.eevolution.api.service

import org.eevolution.UbiquitousLanguage.{Payment, PaymentProcessor}
import org.eevolution.api.repository.Context.ContextEnvironment
import org.eevolution.api.repository.PaymentProcessorRepository
import zio.ZIO

/**
  * API Trait Domain Payment Processor Service
  */
trait PaymentProcessorService {
  def paymentProcessorService : PaymentProcessorService.Service
}

/**
  * API Singleton Object Domain Payment Processor Service
  */
object PaymentProcessorService {

  type PaymentProcessorServiceEnvironment = PaymentProcessorService  with PaymentProcessorRepository with ContextEnvironment

  trait Service {
    def getById(id: Int): ZIO[ContextEnvironment, Throwable, PaymentProcessor]
    def processing (paymentProcessor :PaymentProcessor, payment: Payment) : ZIO[ContextEnvironment, Throwable, PaymentProcessor]
  }

  trait Live extends PaymentProcessorService

  object Live extends PaymentProcessorService.Live {
    def paymentProcessorService: PaymentProcessorService.Service = new Service {
      override def getById(id: Int): ZIO[ContextEnvironment, Throwable, PaymentProcessor] = ???
      override def processing (paymentProcessor :PaymentProcessor, payment: Payment): ZIO[ContextEnvironment, Throwable, PaymentProcessor] = ???
    }
  }
}
