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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.Bank
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.repository.BankRepository.Service
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.ZIO

import scala.util.Try

/**
  * Standard Implementation for Bank Bank Repository
  */
object BankRepository {

  trait Live extends api.repository.BankRepository with BankMapping

  object Live extends Live {
    override def bankRepository: api.repository.BankRepository.Service = new Service {
      override def getById(id: Int): ZIO[ContextEnvironment, Throwable, Bank] =
        ZIO.accessM(
          environment => for {
            ctx <- environment.execution.getContext
            trx <- environment.execution.getTransaction
            bank <- ZIO.fromTry {
              Try {
                //val result = prepare(quoteBank.filter(_.bankId == lift(id)))(trx.getConnection).executeQuery()
                //val bank = new MBank(ctx, result, trx.getTrxName)
                val bank = run(quoteBank.filter(_.bankId == lift(id))).headOption.get
                bank
              }
            }
          } yield bank
        )
    }
  }

}