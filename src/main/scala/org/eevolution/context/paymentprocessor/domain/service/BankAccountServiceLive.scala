package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository
import org.eevolution.context.paymentprocessor.api.service.BankAccountService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{BankAccount, Id}
import zio.RIO

case class BankAccountServiceLive(bankAccountRepositoryService: BankAccountRepository.Service) extends BankAccountService.Service {
  override def getById(id: Id): RIO[Any, Option[BankAccount]] = bankAccountRepositoryService.getById(id)
}
