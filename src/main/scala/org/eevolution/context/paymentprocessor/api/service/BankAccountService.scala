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

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository.BankAccountRepository
import org.eevolution.context.paymentprocessor.domain.service.BankAccountServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{BankAccount, Id}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain Bank Account Service
  */
object BankAccountService {

  type BankAccountService = Has[Service]

  trait Service {
    def getById(id: Id): RIO[Any, Option[BankAccount]]
  }

  def live : ZLayer[BankAccountRepository, Nothing, Has[Service]] =   ZLayer.fromService[BankAccountRepository.Service, Service] {  bankAccountRepositoryService =>
    BankAccountServiceLive (bankAccountRepositoryService)
  }
}
