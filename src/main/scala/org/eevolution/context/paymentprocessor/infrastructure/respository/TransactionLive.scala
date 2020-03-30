package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.compiere.util.Trx
import org.eevolution.context.paymentprocessor.api.repository.Transaction
import zio.Task

import scala.util.Try

object TransactionLive extends Transaction.Service {

  override def create: Task[Trx] = Task.fromTry(
    Try{
      Trx.get(Trx.createTrxName(), true)
    }
  )


  /*override def setTransaction(trxName: String): ZIO[Transaction, Throwable, Trx] = ZIO.fromTry(
    Try{
       Trx.
      }
  )*/

  override def commit: Task[Trx] = ???

  override def rollback: Task[Trx] = ???

  override def getTrxName(trx :Trx): Task[String] = Task.fromTry(
    Try{
      trx.getTrxName
    }
  )

  override def getTrx(trxName: String): Task[Trx] = Task.fromTry(
    Try{
      Trx.get(trxName, false)
    }
  )
}