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
import org.eevolution.context.paymentprocessor.api.repository.UserRepository
import org.eevolution.context.paymentprocessor.api.repository.UserRepository.UserRepository
import org.eevolution.context.paymentprocessor.domain.service.UserServiceLive
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Partner, User}
import zio.{Has, RIO, ZLayer}

/**
  * Standard Implementation for Domain User Domain
  */
object UserService {

  type UserService = Has[Service]

  trait Service {
    def getById(id: Id): RIO[Any, Option[User]]
    def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): RIO[Any, Option[User]]
    def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) : RIO[Any,Option[ User]]
    def save (user:User) : RIO[Any, Option[User]]
  }

  def live : ZLayer[UserRepository, Nothing, Has[Service]] =  ZLayer.fromService[ UserRepository.Service, Service] { ( userRepository) => UserServiceLive (userRepository)}  //ZLayer.fromServices[Context.Service , UserRepository.Service, Service] { (context, userRepository) =>UserServiceLive (context, userRepository)}

  //trait Live extends api.service.UserServiceAPI

  /*object Live extends Live {
     new Service {
      override def getById(id: Id): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.getById(id))

      override def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.getUser(partnerId,userName,accountEmail, password))

      override def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): ZIO[UserServiceEnvironment, Any, User] =  ZIO.accessM(_.get.create(partner,userName,accountEmail,userPassword))

      override def save(user: User): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.save(user))
    }
  }

  def live: NoDeps[Nothing, Has[Live.type]] = ZLayer.succeed(Live)*/

  /*def getById(id: Id): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.getById(id))

   def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.getUser(partnerId,userName,accountEmail, password))

   def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): ZIO[UserServiceEnvironment, Any, User] = ZIO.accessM(_.get.create(partner,userName,accountEmail,userPassword))

   def save(user: User): ZIO[UserServiceEnvironment, Throwable, User] = ZIO.accessM(_.get.save(user))*/

}


