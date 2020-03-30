package org.eevolution.context.paymentprocessor.infrastructure.respository


import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.BankRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.Bank
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.{Task, ZIO}

import scala.util.Try

case class BankRepositoryLive() extends BankRepository.Service with BankMapping{
  override def getById(id: Int): Task[Option[Bank]] =
      for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        bank <- ZIO.fromTry {
          Try {
            //val result = prepare(quoteBank.filter(_.bankId == lift(id)))(trx.getConnection).executeQuery()
            //val bank = new MBank(ctx, result, trx.getTrxName)
            val bank = run(quoteBank.filter(_.bankId == lift(id))).headOption
            bank
          }
        }
      } yield bank
}
