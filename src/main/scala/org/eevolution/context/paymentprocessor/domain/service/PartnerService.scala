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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, Partner}
import org.eevolution.context.paymentprocessor.api
import org.eevolution.context.paymentprocessor.api.service.PartnerService.{PartnerServiceEnvironment, Service}
import zio.ZIO

/**
  * Standard Implementation for Domain Bank Account Service
  */
object PartnerService {

  trait Live extends api.service.PartnerService

  object Live extends Live {
    def partnerService: api.service.PartnerService.Service = new Service {
      override def getById(partnerId: Id): ZIO[PartnerServiceEnvironment, Throwable, Partner] = ZIO.accessM(_.partnerRepository.getById(partnerId))
    }
  }
}
