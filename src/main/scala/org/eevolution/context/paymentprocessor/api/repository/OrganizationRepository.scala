package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Organization}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.respository.OrganizationMapping
import zio.{Has, Task, ZIO, ZLayer}

import scala.util.Try


object OrganizationRepository{

   type OrganizationRepository = Has[OrganizationRepository.Service]


   trait Service {
      def getById(id: Id): Task[Option[Organization]]
   }

   def live : ZLayer[Any,Throwable,Has[Service]] = ZLayer.succeed(
      new Service with OrganizationMapping{
      override def getById(id: Id): Task[Option[Organization]] = for {
         organization <- Task.fromTry {
            Try {
               //val result = prepare(quotePayment.filter(_.paymentId == lift(id)))(trx.getConnectioin).executeQuery()
               //val payment = new MPayment(ctx, result, trx.getTrxName)
               val organization = run(quoteOrganization.filter(_.organizationId == lift(id))).headOption
               organization
            }
         }
      } yield organization
   })


   def getById(id: Id) = ZIO.accessM[OrganizationRepository.Service](_.getById(id))

}
