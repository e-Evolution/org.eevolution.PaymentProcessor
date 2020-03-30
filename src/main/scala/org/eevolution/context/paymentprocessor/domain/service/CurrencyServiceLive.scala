package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.CurrencyRepository
import org.eevolution.context.paymentprocessor.api.service.CurrencyService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Currency, Id}
import zio.RIO

case class CurrencyServiceLive(currencyRepository: CurrencyRepository.Service) extends CurrencyService.Service{

  override def getById(currencyId: Id): RIO[Any, Option[Currency]] = currencyRepository.getById(currencyId)
}
