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


package org.eevolution.domain.service

import org.eevolution.UbiquitousLanguage.{BankAccount, Id}
import org.eevolution.api
import org.eevolution.api.service.BankAccountService.{BankAccountServiceEnvironment, Service}
import zio.ZIO

/**
  * Standard Implementation for Domain Bank Account Service
  */
object BankAccountService {

  trait Live extends api.service.BankAccountService

  object Live extends BankAccountService.Live {
    def bankAccountService: api.service.BankAccountService.Service = new Service {
      override def getById(id: Id): ZIO[BankAccountServiceEnvironment, Throwable, BankAccount] = ZIO.accessM(_.bankAccountRepository.getById(id))
    }
  }

}
