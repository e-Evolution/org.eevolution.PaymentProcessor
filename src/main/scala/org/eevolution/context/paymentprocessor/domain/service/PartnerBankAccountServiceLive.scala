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

import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.api.service.PartnerBankAccountService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import zio.RIO

case class PartnerBankAccountServiceLive(partnerBankAccountRepository: PartnerBankAccountRepository.Service) extends PartnerBankAccountService.Service {
  override def getById(partnerId: Id, bankId: Id, userId: Id, accountName: String, accountEmail: String, creditCardNumber: String): RIO[Any, Option[PartnerBankAccount]] = partnerBankAccountRepository.getById(partnerId, bankId, userId , accountName, accountEmail, creditCardNumber)

  override def create(partnerId: Id, bankId: Id, userId: Id, accountName: String, accountEmail: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Id, creditCardExpYY: Id, creditCardVV: String): RIO[Any, Option[PartnerBankAccount]] =
    partnerBankAccountRepository.create(partnerId, bankId, userId,  accountName, accountEmail, creditCardType, creditCardNumber, creditCardExpMM, creditCardExpYY, creditCardVV)

  override def save(partnerBankAccount: PartnerBankAccount): RIO[Any, Option[PartnerBankAccount]] = partnerBankAccountRepository.save(partnerBankAccount)
}
