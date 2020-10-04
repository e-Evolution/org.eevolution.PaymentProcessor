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

package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.infrastructure.respository.PartnerBankAccountRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Bank Account Repository
  */
object PartnerBankAccountRepository {
  type PartnerBankAccountRepository = Has[PartnerBankAccountRepository.Service]

  trait Service {
    def getById(partnerId: Id, bankId: Id, userId: Id, accountName: String, email: String, creditCardNumber: String): Task[Option[PartnerBankAccount]]

    def create(partnerId: Id, bankId: Id, userId: Id, accountName: String, accountEmail: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Id, creditCardExpYY: Id, creditCardVV: String): Task[Option[PartnerBankAccount]]

    def save(partnerBankAccount: PartnerBankAccount): Task[Option[PartnerBankAccount]]
  }


  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed(PartnerBankAccountRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { contextService => PartnerBankAccountRepositoryLive(contextService) }

}
