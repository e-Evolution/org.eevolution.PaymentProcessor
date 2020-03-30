package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Tenant}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.respository.TenantMapping
import zio.{Has, Task, ZIO, ZLayer}

import scala.util.Try

object TenantRepository{

   type TenantRepository = Has[TenantRepository.Service]


  trait Service {
    def getById(id: Id): Task[Option[Tenant]]
  }

  def live : ZLayer[Any , Throwable,Has[Service]] = ZLayer.succeed(
    new Service with TenantMapping {
    override def getById(id: Id): Task[Option[Tenant]] = Task.fromTry {
      Try {
        //val result = prepare(quotePayment.filter(_.paymentId == lift(id)))(trx.getConnection).executeQuery()
        //val payment = new MPayment(ctx, result, trx.getTrxName)
        val tenant = run(quoteTenant.filter(_.tenantId == lift(id))).headOption
        tenant
      }
    }
  })

  def getById(id: Id) = ZIO.accessM[TenantRepository.Service](_.getById(id))

}
