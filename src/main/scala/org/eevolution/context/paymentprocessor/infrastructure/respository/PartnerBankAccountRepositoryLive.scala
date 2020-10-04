package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.compiere.util.Env
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.domain.factory.PartnerBankAccountBuilder
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.MPartnerBankAccount
import zio.Task

import scala.util.Try

case class PartnerBankAccountRepositoryLive() extends PartnerBankAccountRepository.Service with PartnerBankAccountMapping {
  override def getById(partnerId: Id, bankId: Id, userId: Id, accountName: String, accountEmail: String, creditCardNumber: String): Task[Option[PartnerBankAccount]] =
    for {
      //ctx <- contextService.getContext
      //trx <- contextService.getTransaction
      partnerAccountBank <- Task.fromTry {
        Try {
          //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
          //val bank = new MBankAccount(ctx, result, trx.getTrxName)
          val partnerAccountBank = run(quotePartnerBankAccount.filter(pba =>
              pba.partnerId == lift(partnerId) &&
              pba.bankId == lift(bankId) &&
              pba.userId == lift(userId) &&
              pba.accountEmail == lift(accountEmail) &&
              pba.creditCardNumber == lift(creditCardNumber))).headOption
          partnerAccountBank
        }
      }
    } yield partnerAccountBank

  override def create(partnerId: Id, bankId: Id, userId: Id, accountName: String, accountEmail: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Id, creditCardExpYY: Id, creditCardVV: String): Task[Option[PartnerBankAccount]] =
  for {
    partnerBankAccount <- PartnerBankAccountBuilder()
      .WithPartnerId(partnerId)
      .WithBank(bankId)
      .WithUser(userId)
      .WithAccountName(accountName)
      .WithAccountEMail(accountEmail)
      .WithCreditCardType(creditCardType)
      .WithCreditCardNumber(creditCardNumber)
      .WithCreditCardExpMM(creditCardExpMM)
      .WithCreditCardExpYY(creditCardExpYY)
      .WithCreditCardVerificationCode(creditCardVV)
      .IsACH(false)
      .build()
  } yield partnerBankAccount


  override def save(partnerBankAccount: PartnerBankAccount): Task[Option[PartnerBankAccount]] = Task.fromTry {
    Try {
      val partnerBankAccountModel = new MPartnerBankAccount(Env.getCtx, partnerBankAccount.partnerBankAccountId, null)
      partnerBankAccountModel.setC_Bank_ID(partnerBankAccount.bankId)
      partnerBankAccountModel.setC_BPartner_ID(partnerBankAccount.partnerId)
      partnerBankAccountModel.setAD_User_ID(partnerBankAccount.userId)
      partnerBankAccountModel.setCreditCardNumber(partnerBankAccount.creditCardNumber)
      partnerBankAccountModel.setCreditCardExpMM(partnerBankAccount.creditCardExpMM)
      partnerBankAccountModel.setCreditCardExpYY(partnerBankAccount.creditCardExpYY)
      partnerBankAccountModel.setCreditCardType(partnerBankAccount.creditCardType)
      partnerBankAccountModel.setA_EMail(partnerBankAccount.accountEmail)
      partnerBankAccountModel.setA_Name(partnerBankAccount.accountName)
      partnerBankAccountModel.setAD_Org_ID(partnerBankAccount.organizationId)
      partnerBankAccountModel.saveEx()
      val newPartnerBankAccount = partnerBankAccount.copy(partnerBankAccountId = partnerBankAccountModel.get_ID, uuid = partnerBankAccountModel.getUUID, created = partnerBankAccountModel.getCreated.toLocalDateTime, createdBy = partnerBankAccountModel.getCreatedBy, updated = partnerBankAccountModel.getUpdated.toLocalDateTime, updatedBy = partnerBankAccountModel.getUpdatedBy)
      Option(newPartnerBankAccount)
    }
  }
}
