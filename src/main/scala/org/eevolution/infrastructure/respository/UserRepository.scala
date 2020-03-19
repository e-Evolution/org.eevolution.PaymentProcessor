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

package org.eevolution.infrastructure.respository

import org.eevolution.UbiquitousLanguage.User
import org.eevolution._
import org.eevolution.api.repository.Context.ContextEnvironment
import org.eevolution.api.repository.UserRepository.Service
import org.eevolution.infrastructure.database.context._
import zio.ZIO

import scala.util.Try

object UserRepository {

  trait Live extends api.repository.UserRepository with UserMapping

  object Live extends Live {
    override def userRepository: api.repository.UserRepository.Service = new Service {
      override def getById(id: Int): ZIO[ContextEnvironment, Throwable, User] =
        ZIO.accessM(
          environment => for {
            ctx <- environment.execution.getContext
            trx <- environment.execution.getTransaction
            user <- ZIO.fromTry {
              Try {
                //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
                //val bank = new MBankAccount(ctx, result, trx.getTrxName)
                val user = run(quoteUser.filter(_.userId == lift(id))).headOption.get
                user
              }
            }
          } yield user
        )
    }
  }
}