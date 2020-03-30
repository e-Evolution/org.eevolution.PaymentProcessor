package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Bank, Id}
import org.eevolution.context.paymentprocessor.infrastructure.respository.BankRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Bank Bank Repository
  */
object BankRepository {

  type BankRepository = Has[BankRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[Bank]]
  }

  def live : ZLayer[Any, Throwable, Has[Service]] =   ZLayer.succeed( BankRepositoryLive ()) //ZLayer.fromService[Context.Service , Service] { contextService => BankRepositoryLive (contextService)}

}
