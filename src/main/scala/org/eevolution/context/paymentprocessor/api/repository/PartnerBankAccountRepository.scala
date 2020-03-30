package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.infrastructure.respository.PartnerBankAccountRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Bank Account Repository
  */
object PartnerBankAccountRepository {
  type PartnerBankAccountRepository = Has[PartnerBankAccountRepository.Service]

  trait Service {
    def getById(partnerId: Id, bankId: Id, userId: Id, email: String, accountNo: String, accountName: String): Task[Option[PartnerBankAccount]]

    def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String): Task[Option[PartnerBankAccount]]

    def save(partnerBankAccount: PartnerBankAccount) : Task[Option[PartnerBankAccount]]
  }


  def live: ZLayer[Any, Throwable, Has[Service]] = ZLayer.succeed( PartnerBankAccountRepositoryLive()) //ZLayer.fromService[Context.Service, Service] { contextService => PartnerBankAccountRepositoryLive(contextService) }

}
