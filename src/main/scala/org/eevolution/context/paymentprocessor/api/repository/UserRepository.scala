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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, Partner, User}
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import zio.ZIO

/**
  * API Trait User Repository
  */
trait UserRepository {
  def userRepository: UserRepository.Service
}

/**
  * API Singleton Object User Repository
  */
object UserRepository {

  trait Service {
    def getById(id: Id): ZIO[ContextEnvironment, Throwable, User]
    def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): ZIO[ContextEnvironment, Throwable, User]
    def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) : ZIO[ContextEnvironment, Any, User]
    def save (user:User) : ZIO[ContextEnvironment, Throwable, User]
  }

  trait Live extends UserRepository

  object Live extends UserRepository.Live {
    def userRepository: UserRepository.Service = new Service {
      override def getById(id: Id): ZIO[ContextEnvironment, Throwable, User] = ???

      override def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): ZIO[ContextEnvironment, Throwable, User] = ???

      override def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): ZIO[ContextEnvironment, Any, User] = ???

      override def save(user: User): ZIO[ContextEnvironment, Throwable, User] = ???
    }
  }
}