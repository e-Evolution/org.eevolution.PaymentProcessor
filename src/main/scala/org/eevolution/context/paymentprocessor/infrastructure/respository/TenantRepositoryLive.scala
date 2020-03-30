package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.api.repository.TenantRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Tenant}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

object TenantRepositoryLive extends TenantRepository.Service with TenantMapping {
  override def getById(id: Id): Task[Option[Tenant]] = Task.fromTry {
    Try {
      //val result = prepare(quotePayment.filter(_.paymentId == lift(id)))(trx.getConnection).executeQuery()
      //val payment = new MPayment(ctx, result, trx.getTrxName)
      val tenant = run(quoteTenant.filter(_.tenantId == lift(id))).headOption
      tenant
    }
  }
}