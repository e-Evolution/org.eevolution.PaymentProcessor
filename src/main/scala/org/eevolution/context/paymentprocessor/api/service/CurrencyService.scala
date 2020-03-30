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

import org.eevolution.context.paymentprocessor.api.repository.CurrencyRepository
import org.eevolution.context.paymentprocessor.api.repository.CurrencyRepository.CurrencyRepository
import org.eevolution.context.paymentprocessor.domain.service.CurrencyServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Currency, Id}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain Bank Account Service
  */
object CurrencyService {



  type CurrencyService = Has[CurrencyService.Service]

  trait Service {
    def getById(currencyId: Id): RIO[Any, Option[Currency]]
  }

  def live: ZLayer[CurrencyRepository, Nothing, Has[Service]] = ZLayer.fromService[CurrencyRepository.Service, Service] { currencyRepository => CurrencyServiceLive(currencyRepository)} //ZLayer.fromServices[Context.Service, CurrencyRepository.Service, Service] { (contextService, currencyRepository) =>CurrencyServiceLive(contextService, currencyRepository)}
}
