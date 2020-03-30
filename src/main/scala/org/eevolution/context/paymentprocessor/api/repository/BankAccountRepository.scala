package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.BankAccount
import org.eevolution.context.paymentprocessor.infrastructure.respository.BankAccountRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Bank Account Repository
  */
object BankAccountRepository {

  type BankAccountRepository = Has[BankAccountRepository.Service]


  trait Service {
    def getById(id: Int): Task[Option[BankAccount]]
  }

 def live : ZLayer[Any, Throwable, Has[Service]] =   ZLayer.succeed(BankAccountRepositoryLive ())//ZLayer.fromService[Context.Service , Service] { contextService => BankAccountRepositoryLive (contextService)}

}
