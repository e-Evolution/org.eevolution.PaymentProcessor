package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.Partner
import org.eevolution.context.paymentprocessor.infrastructure.respository.PartnerRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Partner Repository
  */
object PartnerRepository {

  type PartnerRepository = Has[PartnerRepository.Service]

  trait Service {
    def getById(id: Int): Task[Option[Partner]]
  }

  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed(PartnerRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { (contextService) => PartnerRepositoryLive(contextService) }
}
