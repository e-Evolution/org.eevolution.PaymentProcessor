package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.repository.BankRepository
import org.eevolution.context.paymentprocessor.api.service.BankService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Bank, Id}
import zio.RIO

case class BankServiceLive(bankRepository: BankRepository.Service) extends BankService.Service {
  override def getById(id: Id): RIO[Any, Option[Bank]] = bankRepository.getById(id)
}
