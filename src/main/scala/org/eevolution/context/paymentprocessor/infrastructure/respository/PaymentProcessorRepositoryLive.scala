package org.eevolution.context.paymentprocessor.infrastructure.respository


import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PaymentProcessor}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

case class PaymentProcessorRepositoryLive() extends PaymentProcessorRepository.Service with PaymentProcessorMapping {
  override def getById(id: Id): Task[Option[PaymentProcessor]] = for {
    //ctx <- contextService.getContext
    //trx <- contextService.getTransaction
    paymentProcessor <- Task.fromTry {
      Try {
        //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
        //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
        val paymentProcessor = run(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id))).headOption
        paymentProcessor
      }
    }
  } yield paymentProcessor

  override def get(bankAccountId: Id, name: String): Task[Option[PaymentProcessor]] = for {
    //ctx <- contextService.getContext
    //trx <- contextService.getTransaction
    paymentProcessor <- Task.fromTry {
      Try {
        //val result = prepare(quotePaymentProcessor.filter(_.paymentProcessorId == lift(id)))(trx.getConnection).executeQuery()
        //val paymentProcessor = new MPaymentProcessor(ctx, result, trx.getTrxName)
        val paymentProcessor = run(quotePaymentProcessor.filter(paymentProcessor => paymentProcessor.bankAccountId == lift(bankAccountId) && paymentProcessor.name == lift(name))).headOption
        paymentProcessor
      }
    }
  } yield paymentProcessor
}
