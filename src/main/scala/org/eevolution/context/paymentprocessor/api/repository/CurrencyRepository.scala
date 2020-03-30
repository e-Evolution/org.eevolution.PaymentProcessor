package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Currency, Id}
import org.eevolution.context.paymentprocessor.infrastructure.respository.CurrencyRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Currency Repository
  */
object CurrencyRepository {

  type CurrencyRepository = Has[CurrencyRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[Currency]]
  }

  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed(CurrencyRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { (contextService) => CurrencyRepositoryLive(contextService) }

}
