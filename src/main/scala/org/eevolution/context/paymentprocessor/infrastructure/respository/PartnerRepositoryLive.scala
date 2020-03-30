package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.PartnerRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.Partner
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.{Task, ZIO}

import scala.util.Try

case class PartnerRepositoryLive() extends  PartnerRepository.Service with PartnerMapping {
  override def getById(id: Int): Task[Option[Partner]] = for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        partner <- Task.fromTry {
          Try {
            //val result = prepare(quotePartner.filter(_.partnerId == lift(id)))(trx.getConnection).executeQuery()
            //val partner = new MPartner(ctx, result, trx.getTrxName)
            val partner = run(quotePartner.filter(_.partnerId == lift(id))).headOption
            partner
          }
        }
      } yield partner
}
