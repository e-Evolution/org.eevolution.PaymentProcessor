package org.eevolution.context.paymentprocessor.infrastructure.respository


import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository.Service
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.BankAccount
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.{Task, ZIO}

import scala.util.Try

case class BankAccountRepositoryLive() extends BankAccountRepository.Service with BankAccountMapping{
  override def getById(id: Int):  Task[Option[BankAccount]] =
   for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        bank <- ZIO.fromTry {
          Try {
            //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
            //val bank = new MBankAccount(ctx, result, trx.getTrxName)
            val bank : Option[BankAccount] = run(quoteBankAccount.filter(_.bankAccountId == lift(id))).headOption
            bank
          }
        }
      } yield bank
}
