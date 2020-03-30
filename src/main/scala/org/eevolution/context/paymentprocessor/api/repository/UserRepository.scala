package org.eevolution.context.paymentprocessor.api.repository

import org.compiere.util.Env
import org.eevolution.context.paymentprocessor.domain.factory.UserBuilder
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Partner, User}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.MUser
import org.eevolution.context.paymentprocessor.infrastructure.respository.UserMapping
import zio.{Has, Task, ZIO, ZLayer}

import scala.util.Try

object UserRepository{

  type UserRepository = Has[UserRepository.Service]

  trait Service {
    def getById(id: Id): Task[Option[User]]
    def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): Task[ Option[User]]
    def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) : Task[ Option[User]]
    def save (user:User) : Task[Option[User]]
  }
  def live  : ZLayer[Any, Throwable, Has[Service]] =   ZLayer.succeed(
    new Service with UserMapping {
    override def getById(id: Id): Task[Option[User]] = for {
      //ctx <- environment.get.getContext
      //trx <- environment.get.getTransaction
      user <- Task.fromTry {
        Try {
          //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
          //val bank = new MBankAccount(ctx, result, trx.getTrxName)
          val user = run(quoteUser.filter(_.userId == lift(id))).headOption
          user
        }
      }
    } yield user


    override def getUser(partnerId: Id, userName: String, accountEmail: String, password: String): Task[Option[User]] = for {
      user <- Task.fromTry {
        Try {
          val user = run(quoteUser.filter(
            user => user.partnerId == lift(partnerId) &&
              user.name == lift(userName) &&
              user.email == lift(accountEmail) &&
              user.password == lift(password)
          )).headOption
          user
        }
      }
    } yield user

    override def create(partner: Partner, userName: String, accountEmail: String, userPassword: String): Task[Option[User]] = UserBuilder().WithPartnerId(partner.partnerId).WithName(userName).WithEmail(accountEmail).WithPassword(userPassword).build()

    override def save(user: User): Task[Option[User]] = for {
      user <- Task.fromTry {
        Try {
          //val userModel = new MUser(ctx, user.userId, trx.getTrxName)
          val userModel = new MUser(Env.getCtx, user.userId, null)
          userModel.setAD_Org_ID(user.organizationId)
          userModel.setC_BPartner_ID(user.partnerId)
          userModel.setDescription(user.description)
          userModel.setName(user.name)
          userModel.setEMail(user.email)
          userModel.setPassword(user.password)
          user.copy(
            userId = userModel.get_ID,
            uuid = userModel.getUUID,
            createdBy = userModel.getCreatedBy,
            created = userModel.getCreated.toInstant,
            updatedBy = userModel.getUpdatedBy,
            updated = userModel.getUpdated.toInstant
          )
          Option(user)
        }
      }
    } yield user
  })

  def getById(id: Id) = ZIO.accessM[Service](_.getById(id))
  def getUser(partnerId: Id, userName: String, accountEmail: String, password: String) = ZIO.accessM[Service](_.getUser(partnerId  ,userName , accountEmail , password  ))
  def create(partner : Partner , userName : String , accountEmail : String , userPassword : String) = ZIO.accessM[Service](_.create(partner, userName, accountEmail, userPassword))
  def save (user:User) = ZIO.accessM[Service](_.save(user))

}
