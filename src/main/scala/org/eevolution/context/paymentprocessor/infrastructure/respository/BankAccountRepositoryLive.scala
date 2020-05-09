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


import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.BankAccount
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.{Task, ZIO}

import scala.util.Try

case class BankAccountRepositoryLive() extends BankAccountRepository.Service with BankAccountMapping {
  override def getById(id: Int): Task[Option[BankAccount]] =
    for {
      //ctx <- contextService.getContext
      //trx <- contextService.getTransaction
      bank <- ZIO.fromTry {
        Try {
          //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
          //val bank = new MBankAccount(ctx, result, trx.getTrxName)
          val bank: Option[BankAccount] = run(quoteBankAccount.filter(_.bankAccountId == lift(id))).headOption
          bank
        }
      }
    } yield bank
}
