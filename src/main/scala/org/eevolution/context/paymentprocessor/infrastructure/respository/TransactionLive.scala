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

import org.compiere.util.Trx
import org.eevolution.context.paymentprocessor.api.repository.Transaction
import zio.Task

import scala.util.Try

object TransactionLive extends Transaction.Service {

  override def create: Task[Trx] = Task.fromTry(
    Try {
      Trx.get(Trx.createTrxName(), true)
    }
  )


  /*override def setTransaction(trxName: String): ZIO[Transaction, Throwable, Trx] = ZIO.fromTry(
    Try{
       Trx.
      }
  )*/

  override def commit: Task[Trx] = ???

  override def rollback: Task[Trx] = ???

  override def getTrxName(trx: Trx): Task[String] = Task.fromTry(
    Try {
      trx.getTrxName
    }
  )

  override def getTrx(trxName: String): Task[Trx] = Task.fromTry(
    Try {
      Trx.get(trxName, false)
    }
  )
}