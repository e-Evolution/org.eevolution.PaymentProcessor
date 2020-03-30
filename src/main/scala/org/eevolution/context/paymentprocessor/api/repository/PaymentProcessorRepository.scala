package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PaymentProcessor}
import org.eevolution.context.paymentprocessor.infrastructure.respository.PaymentProcessorRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Payment Processor Repository
  */
object PaymentProcessorRepository {

  type PaymentProcessorRepository = Has[PaymentProcessorRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[PaymentProcessor]]

    def get(bankAccountId: Id, name: String): Task[Option[PaymentProcessor]]
  }

  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed(PaymentProcessorRepositoryLive()) //Layer.fromService[Context.Service, Service] { (contextService) => PaymentProcessorRepositoryLive(contextService) }
}
