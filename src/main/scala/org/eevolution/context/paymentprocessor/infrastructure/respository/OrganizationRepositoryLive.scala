package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.api.repository.OrganizationRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Organization}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

final case class OrganizationRepositoryLive() extends OrganizationRepository.Service with OrganizationMapping {
  override def getById(id: Id): Task[Option[Organization]] =
    for {
      tenant <- Task.fromTry {
        Try {
          //val result = prepare(quotePayment.filter(_.paymentId == lift(id)))(trx.getConnectioin).executeQuery()
          //val payment = new MPayment(ctx, result, trx.getTrxName)
          val tenant = run(quoteOrganization.filter(_.organizationId == lift(id))).headOption
          tenant
        }
      }
    } yield tenant
}