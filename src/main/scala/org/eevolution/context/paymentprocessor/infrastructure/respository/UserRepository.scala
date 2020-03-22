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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, Partner, PartnerBankAccount, User}
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.api.repository.UserRepository.Service
import org.eevolution.context.paymentprocessor.domain.factory.UserBuilder
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.{MPartnerBankAccount, MUser}
import zio.ZIO

import scala.util.Try

object UserRepository {

  trait Live extends org.eevolution.context.paymentprocessor.api.repository.UserRepository with UserMapping

  object Live extends Live {
    override def userRepository: org.eevolution.context.paymentprocessor.api.repository.UserRepository.Service = new Service {
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

      override def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): ZIO[ContextEnvironment, Throwable, User] = ZIO.accessM(
        environment => for {
          user <- ZIO.fromTry {
            Try {
              val user = run(quoteUser.filter(
                user => user.partnerId == lift(partnerId) &&
                  user.name == lift(userName) &&
                  user.email == lift(accountEmail) &&
                  user.password == lift(password)
              )).headOption.get
              user
            }
          }
        } yield user)

      override def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): ZIO[ContextEnvironment, Any, User] = UserBuilder().WithPartnerId(partner.partnerId).WithName(userName).WithEmail(accountEmail).WithPassword(userPassword).build()

      override def save(user: User): ZIO[ContextEnvironment, Throwable, User] = ZIO.accessM(
        environment => for {
          ctx <- environment.execution.getContext
          trx <- environment.execution.getTransaction
          user <- ZIO.fromTry {
            Try {
              val userModel = new MUser(ctx, user.userId, trx.getTrxName)
              userModel.setAD_Org_ID(user.organizationId)
              userModel.setC_BPartner_ID(user.partnerId)
              userModel.setDescription(user.description)
              userModel.setName(user.name)
              userModel.setEMail(user.email)
              userModel.setPassword(user.password)
              user.copy(
                userId = userModel.get_ID,
                uuid = userModel.getUUID,
                createdBy = userModel.getCreatedBy,
                created = userModel.getCreated.toInstant,
                updatedBy = userModel.getUpdatedBy,
                updated = userModel.getUpdated.toInstant
              )
              user
            }
          }
        } yield user
      )
    }
  }

}