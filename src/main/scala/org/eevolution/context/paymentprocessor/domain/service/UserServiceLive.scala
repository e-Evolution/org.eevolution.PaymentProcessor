package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.UserRepository
import org.eevolution.context.paymentprocessor.api.service.UserService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Partner, User}
import zio.RIO

final case class UserServiceLive (userRepository : UserRepository.Service) extends UserService.Service {
  override def getById(id: Id): RIO[Any, Option[User]] = userRepository.getById(id)

  override def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): RIO[Any, Option[User]] = userRepository.getUser(partnerId, userName, accountEmail, password)

  override def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): RIO[Any,Option[User]] = userRepository.create(partner,userName,accountEmail, userPassword)

  override def save(user: User): RIO[Any, Option[User]] = userRepository.save(user)

}