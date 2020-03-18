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
package org.eevolution.api.service

import org.eevolution.UbiquitousLanguage.{Id, User}
import org.eevolution.api.repository.Context.ContextEnvironment
import org.eevolution.api.repository.UserRepository
import zio.ZIO

/**
  * API Trait Domain User Service
  */
trait UserService {
  def userService: UserService.Service
}

/**
  * API Singleton Object Domain User Service
  */
object UserService {

  type UserServiceEnvironment = UserService with UserRepository with ContextEnvironment

  trait Service {
    def getById(id: Id): ZIO[UserServiceEnvironment, Throwable, User]
  }

  trait Live extends UserService

  object Live extends UserService.Live {
    def userService: UserService.Service = new Service {
      override def getById(id: Id): ZIO[UserServiceEnvironment, Throwable, User] = ???
    }
  }

}

