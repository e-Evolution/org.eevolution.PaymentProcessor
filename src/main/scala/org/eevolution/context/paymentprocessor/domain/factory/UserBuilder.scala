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

package org.eevolution.context.paymentprocessor.domain.factory


import java.time.Instant

import org.eevolution.context.paymentprocessor.UbiquitousLanguage._
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.domain.model.User
import zio.ZIO

object UserBuilder {

  def apply() = new Builder[Required, Maybe, Maybe, Maybe, Maybe]()

  case class Builder[
    WithNameTracking <: Maybe,
    WithPartnerIdTracking <: Maybe,
    WithDescriptionTracking <: Maybe,
    WithEmailTracking <: Maybe,
    WithPasswordTracking <: Maybe,
  ](
     maybeName: Option[String] = None,
     maybePartnerId: Option[Id] = None,
     maybeDescription: Option[String] = None,
     maybeEmail: Option[String] = None,
     maybePassword: Option[String] = None
   ) {

    type IsOnce[T] = =:=[T, Once]
    type IsMandatory[T] = =:=[T, Required]

    def WithName[Name <: WithNameTracking : IsMandatory](name: String) =
      copy[Once,
        WithPartnerIdTracking,
        WithDescriptionTracking,
        WithEmailTracking,
        WithPasswordTracking](maybeName = Some(name))


    def WithPartnerId[PartnerId <: WithNameTracking : IsMandatory](partnerId: Id) =
      copy[
        WithNameTracking,
        Once,
        WithDescriptionTracking,
        WithEmailTracking,
        WithPasswordTracking](maybePartnerId = Some(partnerId))


    def WithDescription[Description <: WithDescriptionTracking: IsMandatory](description: String) =
      copy[WithNameTracking,
        WithPartnerIdTracking,
        Once,
        WithEmailTracking,
        WithPasswordTracking](maybeDescription = Some(description))

    def WithEmail[Email <: WithEmailTracking : IsMandatory](email: String) =
      copy[WithNameTracking,
        WithPartnerIdTracking,
        WithDescriptionTracking,
        Once,
        WithPasswordTracking](maybeEmail = Some(email))

    def WithPassword[Password <: WithPasswordTracking : IsMandatory](password: String) =
      copy[WithNameTracking,
        WithPartnerIdTracking,
        WithDescriptionTracking,
        WithEmailTracking,
        Once](maybePassword = Some(password))

  def build[
    Name <: WithNameTracking: IsOnce,
    PartnerId <: WithPartnerIdTracking: IsOnce,
    Description <: WithDescriptionTracking : IsOnce,
    Email <: WithEmailTracking : IsOnce,
    Password <: WithPasswordTracking : IsOnce
  ](): ZIO[ContextEnvironment, Any, User] = ZIO.accessM {
    environment =>
      for {
        tenant <- environment.execution.getTenant
        organization <- environment.execution.getOrganization
        user <- environment.execution.getUser
        name <- ZIO.fromOption(maybeName)
        partnerId <- ZIO.fromOption(maybePartnerId)
        description <- ZIO.fromOption(maybeDescription)
        email <- ZIO.fromOption(maybeEmail)
        password <- ZIO.fromOption(maybePassword)
        user <- ZIO.effectTotal(
        User.create(0, partnerId , tenant.tenantId , organization.organizationId, true, Instant.now() ,user.userId , Instant.now() , user.userId,name,description,email,password, null ))
      } yield user
  }
}

}



