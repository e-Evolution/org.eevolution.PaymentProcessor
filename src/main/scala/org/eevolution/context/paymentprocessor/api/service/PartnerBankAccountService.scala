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


package org.eevolution.context.paymentprocessor.api.service

import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.domain.service.PartnerBankAccountServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain Bank Account Service
  */
object PartnerBankAccountService {


  type PartnerBankAccountService = Has[Service]

  trait Service {

    def getById(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String): RIO[Any, Option[PartnerBankAccount]]

    def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String): RIO[Any, Option[PartnerBankAccount]]

    def save(partnerBankAccount: PartnerBankAccount): RIO[Any,Option[ PartnerBankAccount]]
  }


  def live: ZLayer[ PartnerBankAccountRepository, Throwable, Has[Service]] =  ZLayer.fromService[PartnerBankAccountRepository.Service, Service] {  (partnerBankAccountRepository) => PartnerBankAccountServiceLive(partnerBankAccountRepository)}
  //ZLayer.fromServices[Context.Service, PartnerBankAccountRepository.Service, Service] { (contextService, partnerBankAccountRepository) =>PartnerBankAccountServiceLive(contextService, partnerBankAccountRepository)}

}
