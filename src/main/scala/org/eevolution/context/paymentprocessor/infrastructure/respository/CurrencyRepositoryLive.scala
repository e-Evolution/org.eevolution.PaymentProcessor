package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.CurrencyRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Currency, Id}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

case class CurrencyRepositoryLive() extends CurrencyRepository.Service with CurrencyMapping{
  override def getById(id: Id):  Task[Option[Currency]] =for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        currency <- Task.fromTry {
          Try {
            //val result = prepare(quoteBank.filter(_.bankId == lift(id)))(trx.getConnection).executeQuery()
            //val bank = new MBank(ctx, result, trx.getTrxName)
            val currency = run(quoteCurrency.filter(_.currencyId == lift(id))).headOption
            currency
          }
        }
      } yield currency
}
