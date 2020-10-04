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

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Partner, User}
import org.eevolution.context.paymentprocessor.infrastructure.respository.UserRepositoryLive
import zio.{Has, Task, ZIO, ZLayer}

object UserRepository{

  type UserRepository = Has[UserRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[User]]
    def getUser(partnerId: Id, userName: String, accountEmail: String): Task[ Option[User]]
    def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) : Task[ Option[User]]
    def save (user:User) : Task[Option[User]]
  }
  def live  : ZLayer[Any, Throwable, Has[Service]] =  ZLayer.succeed(UserRepositoryLive())

  def getById(id: Id) = ZIO.accessM[Service](_.getById(id))
  def getUser(partnerId: Id, userName: String, accountEmail: String) = ZIO.accessM[Service](_.getUser(partnerId, userName, accountEmail))
  def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) = ZIO.accessM[Service](_.create(partner, userName, accountEmail, userPassword))
  def save (user:User) = ZIO.accessM[Service](_.save(user))

}
