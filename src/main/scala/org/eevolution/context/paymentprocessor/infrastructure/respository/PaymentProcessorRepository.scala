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

package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, PaymentProcessor}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository.Service
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.ZIO

import scala.util.Try

/**
  * Standard Implementation for Payment Processor Repository
  */
object PaymentProcessorRepository {

  trait Live extends api.repository.PaymentProcessorRepository with PaymentProcessorMapping

  object Live extends Live {
    override def paymentProcessorRepository: api.repository.PaymentProcessorRepository.Service = new Service {
      override def getById(id: Int): ZIO[ContextEnvironment, Throwable, PaymentProcessor] =
        ZIO.accessM(
          environment => for {
            ctx <- environment.execution.getContext
            trx <- environment.execution.getTransaction
            paymentProcessor <- ZIO.fromTry {
              Try {
                //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
                //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
                val paymentProcessor = run(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id))).headOption.get
                paymentProcessor
              }
            }
          } yield paymentProcessor
        )

      override def get(bankAccountId: Id, name: String): ZIO[ContextEnvironment, Throwable, PaymentProcessor] =    ZIO.accessM(
        environment => for {
          ctx <- environment.execution.getContext
          trx <- environment.execution.getTransaction
          paymentProcessor <- ZIO.fromTry {
            Try {
              //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
              //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
              val paymentProcessor = run(quotePaymentProcessor.filter(paymentProcessor => paymentProcessor.bankAccountId == lift(bankAccountId) && paymentProcessor.name == lift(name))).headOption.get
              paymentProcessor
            }
          }
        } yield paymentProcessor
      )
    }
  }

}