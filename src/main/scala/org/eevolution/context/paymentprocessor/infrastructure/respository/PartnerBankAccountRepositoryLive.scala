package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.compiere.util.Env
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.domain.factory.PartnerBankAccountBuilder
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.MPartnerBankAccount
import zio.{RIO, Task}

import scala.util.Try

case class PartnerBankAccountRepositoryLive()  extends PartnerBankAccountRepository.Service with PartnerBankAccountMapping {
  override def getById(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String): Task[Option[PartnerBankAccount]] =
      for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        partnerAccountBank <- Task.fromTry {
          Try {
            //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
            //val bank = new MBankAccount(ctx, result, trx.getTrxName)
            val partnerAccountBank = run(quotePartnerBankAccount.filter(partnerBankAccount =>
              partnerBankAccount.partnerId == lift(partnerId) &&
                partnerBankAccount.bankId == lift(bankId) &&
                partnerBankAccount.accountEmail == lift(accountEmail) &&
                partnerBankAccount.accountNo == lift(accountNo) &&
                partnerBankAccount.accountName == lift(accountName))).headOption
            partnerAccountBank
          }
        }
      } yield partnerAccountBank

  override def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String):  Task[Option[PartnerBankAccount]] = for {
      //context <- contextService.getContext
      //trx <- contextService.getTransaction
      //tenant <- contextService.getTenant
      //organization <- contextService.getOrganization
      partnerBankAccount <- PartnerBankAccountBuilder().WithPartnerId(partnerId).WithBank(bankId).WithAccountEMail(accountEmail).WithAccountNo(accountNo).WithAccountName(accountName).IsACH(false).build()
    } yield partnerBankAccount

  override def save(partnerBankAccount: PartnerBankAccount):  Task[Option[PartnerBankAccount]] =
     for {
      //ctx <- contextService.getContext
      //trx <- contextService.getTransaction
      partnerAccountBank <- Task.fromTry {
        Try {
          val partnerBankAccountModel = new MPartnerBankAccount(Env.getCtx, partnerBankAccount.partnerBankAccountId, null)
          partnerBankAccountModel.setC_Bank_ID(partnerBankAccount.bankId)
          partnerBankAccountModel.setC_BPartner_ID(partnerBankAccount.partnerId)
          partnerBankAccountModel.setAD_User_ID(partnerBankAccount.userId)
          partnerBankAccountModel.setCreditCardNumber(partnerBankAccount.CreditCardNumber)
          partnerBankAccountModel.setCreditCardExpMM(partnerBankAccount.creditCardExpMM)
          partnerBankAccountModel.setCreditCardExpYY(partnerBankAccount.creditCardExpYY)
          partnerBankAccountModel.setCreditCardType(partnerBankAccount.creditCardType)
          partnerBankAccountModel.setAccountNo(partnerBankAccount.accountNo)
          partnerBankAccountModel.setA_EMail(partnerBankAccount.accountNo)
          partnerBankAccountModel.setA_Name(partnerBankAccount.accountName)
          partnerBankAccountModel.setAD_Org_ID(partnerBankAccount.organizationId)
          partnerBankAccountModel.saveEx()
          partnerBankAccount.copy(
            partnerBankAccountId = partnerBankAccountModel.get_ID,
            uuid = partnerBankAccountModel.getUUID,
            createdBy = partnerBankAccountModel.getCreatedBy,
            created = partnerBankAccountModel.getCreated.toInstant ,
            updatedBy = partnerBankAccountModel.getUpdatedBy ,
            updated = partnerBankAccountModel.getUpdated.toInstant)
          Option(partnerBankAccount)
        }
      }
    } yield partnerAccountBank
}
