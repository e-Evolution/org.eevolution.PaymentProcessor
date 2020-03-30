package org.eevolution.context.paymentprocessor.api

import java.util.Properties

import org.compiere.util.{Env, Trx}
import org.eevolution.context.paymentprocessor.api.repository.OrganizationRepository.OrganizationRepository
import org.eevolution.context.paymentprocessor.api.repository.TenantRepository.TenantRepository
import org.eevolution.context.paymentprocessor.api.repository.UserRepository.UserRepository
import org.eevolution.context.paymentprocessor.api.repository.{OrganizationRepository, TenantRepository, UserRepository}
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Organization, Payment, Tenant, User}
import zio.{Has, RIO, Task, ZEnv, ZLayer}

import scala.util.Try

object Context {


  type Context = Has[Service]


  trait Service {
    def getContext: Task[Option[Properties]] = ???

    def setTransaction(trxName: String):Task[Option[Trx]] = ???

    def getTransaction:  Task[Option[Trx]] = ???

    def getTenant: RIO[TenantRepository, Option[Tenant]] = ???

    def getOrganization: RIO[OrganizationRepository, Option[Organization]] = ???

    def getUser: RIO[UserRepository, Option[User]] = ???
  }


  def live : ZLayer[TenantRepository with OrganizationRepository with UserRepository, Throwable, Has[Service]] =
    ZLayer.fromServices[TenantRepository.Service, OrganizationRepository.Service, UserRepository.Service, Service] {
      (tenantRepository, organizationRepository, userRepository ) => Live(tenantRepository, organizationRepository , userRepository)
    }

}


case class Live(tenantRepository: TenantRepository.Service, organizationRepository: OrganizationRepository.Service, userRepository: UserRepository.Service) extends Context.Service {


  var maybeTrx: Option[Trx] = None

  override def getContext: Task[Option[Properties]] = Task.effectTotal(
    Option(Env.getCtx)
  )

  override def setTransaction(trxName: String): Task[Option[Trx]] = Task.fromTry(
    Try {
      if (maybeTrx.isDefined)
        maybeTrx
      else {

        maybeTrx = Option(Trx.get(trxName, false))
        maybeTrx
      }
    }
  )

  override def getTransaction: Task[Option[Trx]] = Task.succeed(maybeTrx)

  override def getTenant: RIO[TenantRepository, Option[Tenant]] = for {
    tenantId <- RIO.effectTotal(Env.getAD_Client_ID(Env.getCtx))
    tenant <- tenantRepository.getById(tenantId)
  } yield tenant

  override def getOrganization: RIO[OrganizationRepository, Option[Organization]] = for {
    organizationId <- RIO.effectTotal(Env.getAD_Org_ID(Env.getCtx))
    organization <- organizationRepository.getById(organizationId)
  } yield organization


  override def getUser: Task[Option[User]] = for {
    userId <- RIO.effectTotal(Env.getAD_User_ID(Env.getCtx))
    user <- userRepository.getById(userId)
  } yield user


}