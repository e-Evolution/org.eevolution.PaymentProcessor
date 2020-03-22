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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository.Service
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.domain.factory.PartnerBankAccountBuilder
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.MPartnerBankAccount
import zio.ZIO

import scala.util.Try

/**
  * Standard Implementation for Bank Account Repository
  */
object PartnerBankAccountRepository {

  trait Live extends api.repository.PartnerBankAccountRepository with PartnerBankAccountMapping

  object Live extends Live {
    override def partnerBankAccountRepository: api.repository.PartnerBankAccountRepository.Service = new Service {
      override def getById(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String): ZIO[ContextEnvironment, Throwable, PartnerBankAccount] =
        ZIO.accessM(
          environment => for {
            ctx <- environment.execution.getContext
            trx <- environment.execution.getTransaction
            partnerAccountBank <- ZIO.fromTry {
              Try {
                //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
                //val bank = new MBankAccount(ctx, result, trx.getTrxName)
                val partnerAccountBank = run(quotePartnerBankAccount.filter(partnerBankAccount =>
                  partnerBankAccount.partnerId == lift(partnerId) &&
                    partnerBankAccount.bankId == lift(bankId) &&
                    partnerBankAccount.accountEmail == lift(accountEmail) &&
                    partnerBankAccount.accountNo == lift(accountNo) &&
                    partnerBankAccount.accountName == lift(accountName))).headOption.get
                partnerAccountBank
              }
            }
          } yield partnerAccountBank
        )

      override def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String): ZIO[ContextEnvironment, Any, PartnerBankAccount] = ZIO.accessM(environment =>
        for {
          context <- environment.execution.getContext
          trx <- environment.execution.getTransaction
          tenant <- environment.execution.getTenant
          organization <- environment.execution.getOrganization
          partnerBankAccount <- PartnerBankAccountBuilder().WithPartnerId(partnerId).WithBank(bankId).WithAccountEMail(accountEmail).WithAccountNo(accountNo).WithAccountName(accountName).IsACH(false).build()
        } yield partnerBankAccount
      )

      override def save(partnerBankAccount: PartnerBankAccount): ZIO[ContextEnvironment, Throwable, PartnerBankAccount] = ZIO.accessM(
        environment => for {
          ctx <- environment.execution.getContext
          trx <- environment.execution.getTransaction
          partnerAccountBank <- ZIO.fromTry {
            Try {
              val partnerBankAccountModel = new MPartnerBankAccount(ctx, partnerBankAccount.partnerBankAccountId, trx.getTrxName)
              partnerBankAccountModel.setC_Bank_ID(partnerBankAccount.bankId)
              partnerBankAccountModel.setC_BPartner_ID(partnerBankAccount.partnerId)
              partnerBankAccountModel.setAD_User_ID(partnerBankAccount.userId)
              partnerBankAccountModel.setCreditCardNumber(partnerBankAccount.CreditCardNumber)
              partnerBankAccountModel.setCreditCardExpMM(partnerBankAccount.creditCardExpMM)
              partnerBankAccountModel.setCreditCardExpYY(partnerBankAccount.creditCardExpYY)
              partnerBankAccountModel.setCreditCardType(partnerBankAccount.creditCardType)
              partnerBankAccountModel.setAccountNo(partnerBankAccount.accountNo)
              partnerBankAccountModel.setA_EMail(partnerBankAccount.accountNo)
              partnerBankAccountModel.setA_Name(partnerBankAccount.accountName)
              partnerBankAccountModel.setAD_Org_ID(partnerBankAccount.organizationId)
              partnerBankAccountModel.saveEx()
              partnerBankAccount.copy(
                partnerBankAccountId = partnerBankAccountModel.get_ID,
                uuid = partnerBankAccountModel.getUUID,
                createdBy = partnerBankAccountModel.getCreatedBy,
                created = partnerBankAccountModel.getCreated.toInstant ,
                updatedBy = partnerBankAccountModel.getUpdatedBy ,
                updated = partnerBankAccountModel.getUpdated.toInstant)
              partnerBankAccount
            }
          }
        } yield partnerAccountBank
      )
    }
  }
}