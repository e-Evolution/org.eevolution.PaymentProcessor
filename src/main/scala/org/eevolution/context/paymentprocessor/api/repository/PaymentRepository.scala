package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.model.Payment
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.Id
import org.eevolution.context.paymentprocessor.infrastructure.respository.PaymentRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Payment Repository
  */
object PaymentRepository {


  type PaymentRepository = Has[PaymentRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[Payment]]
    def save(payment : Payment) : Task[Option[Payment]]
  }

  def live : ZLayer[Any , Throwable, Has[Service]] = ZLayer.succeed(PaymentRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { (contextService) => PaymentRepositoryLive (contextService)}
}
