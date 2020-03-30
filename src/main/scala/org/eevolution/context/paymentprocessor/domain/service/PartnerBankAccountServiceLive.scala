package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.api.service.PartnerBankAccountService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, PartnerBankAccount}
import zio.RIO

case class PartnerBankAccountServiceLive(partnerBankAccountRepository: PartnerBankAccountRepository.Service) extends PartnerBankAccountService.Service {
  override def getById(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String): RIO[Any, Option[PartnerBankAccount]] = partnerBankAccountRepository.getById(partnerId, bankId, userId, accountEmail, accountNo, accountName)

  override def create(partnerId: Id, bankId: Id, userId: Id, accountEmail: String, accountNo: String, accountName: String, creditCardType: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, creditCardVV: String): RIO[Any, Option[PartnerBankAccount]] =
    partnerBankAccountRepository.create(partnerId, bankId, userId, accountEmail, accountNo, accountName, creditCardType, creditCardNumber, creditCardExpMM, creditCardExpYY, creditCardVV)

  override def save(partnerBankAccount: PartnerBankAccount): RIO[Any, Option[PartnerBankAccount]] = partnerBankAccountRepository.save(partnerBankAccount)
}
