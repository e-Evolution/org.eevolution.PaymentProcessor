package org.eevolution.context.paymentprocessor.api.repository

import org.compiere.util.Trx
import zio.ZLayer.NoDeps
import zio.{Has, Task, ZLayer}

import scala.util.Try

object Transaction {

  type Transaction = Has[Transaction.Service]


  trait Service {
    def create: Task[Trx] = ???

    def getTrxName(trx: Trx): Task[String] = ???

    def getTrx(trxName: String): Task[Trx] = ???

    //def setTransaction(trxName: String): ZIO[Transaction, Throwable, Trx]

    def commit: Task[Trx] = ???

    def rollback: Task[Trx] = ???
  }

  def live: NoDeps[Nothing, Has[Service]] = ZLayer.succeed(
    new Service {
      override def create: Task[Trx] = Task.fromTry(
        Try {
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

      override def getTrxName(trx: Trx): Task[String] = Task.fromTry(
        Try {
          trx.getTrxName
        }
      )

      override def getTrx(trxName: String): Task[Trx] = Task.fromTry(
        Try {
          Trx.get(trxName, false)
        }
      )
    }
  )
}
