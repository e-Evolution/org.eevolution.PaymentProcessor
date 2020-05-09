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


import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PaymentProcessor}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

case class PaymentProcessorRepositoryLive() extends PaymentProcessorRepository.Service with PaymentProcessorMapping {
  override def getById(id: Id): Task[Option[PaymentProcessor]] = for {
    //ctx <- contextService.getContext
    //trx <- contextService.getTransaction
    paymentProcessor <- Task.fromTry {
      Try {
        //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
        //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
        val paymentProcessor = run(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id))).headOption
        paymentProcessor
      }
    }
  } yield paymentProcessor

  override def get(bankAccountId: Id, name: String): Task[Option[PaymentProcessor]] = for {
    //ctx <- contextService.getContext
    //trx <- contextService.getTransaction
    paymentProcessor <- Task.fromTry {
      Try {
        //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
        //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
        val paymentProcessor = run(quotePaymentProcessor.filter(paymentProcessor => paymentProcessor.bankAccountId == lift(bankAccountId) && paymentProcessor.name == lift(name))).headOption
        paymentProcessor
      }
    }
  } yield paymentProcessor
}
