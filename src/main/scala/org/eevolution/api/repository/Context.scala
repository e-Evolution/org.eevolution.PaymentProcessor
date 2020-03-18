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


package org.eevolution.api.repository

import java.util.Properties

import org.compiere.util.Trx
import org.eevolution.UbiquitousLanguage.{Organization, Tenant, User}
import zio.ZIO

/**
  * API Trait Context Application
  */
trait Context {
  val execution  : Context.Execution
}

/**
  * API Singleton Object Context Application
  */
object Context {

  type ContextEnvironment = Context with Transaction with TenantRepository with OrganizationRepository

  trait Execution {
    def getContext: ZIO[Any, Throwable, Properties]
    def getTransaction: ZIO[ContextEnvironment, Throwable, Trx]
    def getTenant: ZIO[ContextEnvironment, Throwable, Tenant]
    def getOrganization: ZIO[ContextEnvironment, Throwable, Organization]
    def getUser: ZIO[ContextEnvironment, Throwable, User]
  }
}
