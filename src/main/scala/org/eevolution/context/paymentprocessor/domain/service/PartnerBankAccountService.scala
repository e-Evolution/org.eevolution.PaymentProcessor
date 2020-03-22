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


package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.service.PartnerBankAccountService.{PartnerBankAccountServiceEnvironment, Service}
import zio.ZIO

/**
  * Standard Implementation for Domain Bank Account Service
  */
object PartnerBankAccountService {

  trait Live extends api.service.PartnerBankAccountService

  object Live extends PartnerBankAccountService.Live {
    def partnerBankAccountService: api.service.PartnerBankAccountService.Service = new Service {
      override def getById(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String): ZIO[PartnerBankAccountServiceEnvironment, Any, PartnerBankAccount] =
        ZIO.accessM(_.partnerBankAccountRepository.getById(partnerId, bankId, userId, accountEmail, accountNo, accountName))

      override def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String): ZIO[PartnerBankAccountServiceEnvironment, Any, PartnerBankAccount] =
        ZIO.accessM(_.partnerBankAccountRepository.create(partnerId, bankId, userId, accountEmail, accountNo, accountName, creditCardType, creditCardNumber, creditCardExpMM, creditCardExpYY, creditCardVV))

      override def save(partnerBankAccount: PartnerBankAccount): ZIO[PartnerBankAccountServiceEnvironment, Throwable, PartnerBankAccount] =  ZIO.accessM(_.partnerBankAccountRepository.save(partnerBankAccount))
    }
  }

}
