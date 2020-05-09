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

import org.eevolution.context.paymentprocessor.api.repository.BankRepository
import org.eevolution.context.paymentprocessor.api.repository.BankRepository.BankRepository
import org.eevolution.context.paymentprocessor.domain.service.BankServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Bank, Id}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain Bank Account Service
  */
object BankService {

  type BankService = Has[Service]

  trait Service {
    def getById(id: Id): RIO[Any, Option[Bank]]
  }

  def live: ZLayer[BankRepository, Nothing, Has[Service]] = ZLayer.fromService[BankRepository.Service, Service] { bankRepository => BankServiceLive(bankRepository) }
}
