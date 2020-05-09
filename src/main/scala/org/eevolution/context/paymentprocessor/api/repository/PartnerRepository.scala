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

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.Partner
import org.eevolution.context.paymentprocessor.infrastructure.respository.PartnerRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Partner Repository
  */
object PartnerRepository {

  type PartnerRepository = Has[PartnerRepository.Service]

  trait Service {
    def getById(id: Int): Task[Option[Partner]]
  }

  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed(PartnerRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { (contextService) => PartnerRepositoryLive(contextService) }
}
