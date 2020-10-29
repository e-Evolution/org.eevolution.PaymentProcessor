/**
  * Copyright (C) 2003-2020, e-Evolution Consultants S.A. , http://www.e-evolution.com
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * Email: victor.perez@e-evolution.com, http://www.e-evolution.com , http://github.com/e-Evolution
  * Created by victor.perez@e-evolution.com , www.e-evolution.com
  **/

package org.eevolution.context.paymentprocessor.infrastructure.process


import java.util.logging.Level

import org.adempiere.exceptions
import org.adempiere.exceptions.AdempiereException
import org.compiere.Adempiere
import org.compiere.model.I_C_Payment
import org.compiere.util.{CLogMgt, Env, Ini, Login}
import org.eevolution.context.paymentprocessor.api.repository.BankAccountRepository.BankAccountRepository
import org.eevolution.context.paymentprocessor.api.repository.BankRepository.BankRepository
import org.eevolution.context.paymentprocessor.api.repository.CurrencyRepository.CurrencyRepository
import org.eevolution.context.paymentprocessor.api.repository.PartnerBankAccountRepository.PartnerBankAccountRepository
import org.eevolution.context.paymentprocessor.api.repository.PartnerRepository.PartnerRepository
import org.eevolution.context.paymentprocessor.api.repository.PaymentProcessorRepository.PaymentProcessorRepository
import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository.PaymentRepository
import org.eevolution.context.paymentprocessor.api.repository.UserRepository.UserRepository
import org.eevolution.context.paymentprocessor.api.repository.{BankAccountRepository, BankRepository, CurrencyRepository, PartnerBankAccountRepository, PartnerRepository, PaymentProcessorRepository, PaymentRepository, UserRepository}
import org.eevolution.context.paymentprocessor.api.service.BankAccountService.BankAccountService
import org.eevolution.context.paymentprocessor.api.service.BankService.BankService
import org.eevolution.context.paymentprocessor.api.service.PaymentProcessorService.PaymentProcessorService
import org.eevolution.context.paymentprocessor.api.service.PaymentService.PaymentService
import org.eevolution.context.paymentprocessor.api.service.SecurityService.SecurityService
import org.eevolution.context.paymentprocessor.api.service.{BankAccountService, BankService, CurrencyService, PartnerBankAccountService, PartnerService, PaymentProcessorService, PaymentService, SecurityService, UserService}
import org.eevolution.service.dsl.ProcessBuilder
import zio.{Ref, Runtime, ZIO}

/**
  * Payment Process Online
  */
class PaymentOnlineService extends PaymentOnlineAbstract {

  override def prepare(): Unit = super.prepare()

  override def doIt(): String = {
    type processEnvironment = PaymentProcessorRepository
      with SecurityService
      with PaymentRepository
      with BankAccountRepository
      with BankRepository
      with PartnerRepository
      with PartnerBankAccountRepository
      with CurrencyRepository
      with UserRepository

    val processServices =
      PaymentProcessorRepository.live ++
        SecurityService.live ++
        PaymentService.live ++
        BankAccountService.live ++
        BankService.live ++
        PartnerService.live ++
        PartnerBankAccountService.live ++
        CurrencyService.live ++
        UserService.live

    def program: ZIO[processEnvironment, Throwable, String] = (for {
      process <- Ref.make(this).flatMap(reference => reference.get)
      maybePayment <- ZIO.accessM[PaymentService](_.get.getById(process.getRecord_ID)).provideSomeLayer(PaymentService.live)
      payment <- maybePayment match {
        case Some(payment)  => if (payment.isApproved) ZIO.fail(new exceptions.AdempiereException(s"@PaymentFormController: PaymentNotCompletedAfterProcessApproval@ ${payment.documentNo}"))  else ZIO.succeed(payment)
        case None  => ZIO.fail(new exceptions.AdempiereException("@OnlinePaymentFailed@ @C_Payment_ID@ @NotFound@"))
      }
      maybeBankAccount <- ZIO.accessM[BankAccountService](_.get.getById(payment.bankAccountId)).provideSomeLayer(BankAccountService.live)
      maybeBank <- ZIO.accessM[BankService](_.get.getById(maybeBankAccount.get.bankId)).provideSomeLayer(BankService.live)
      result <- maybeBank match {
        case Some(bank) if bank.name.equals("PayPal") =>
          ZIO.accessM[PaymentProcessorService](_.get.processing(process.getRecord_ID)).provideSomeLayer(processServices >>> PaymentProcessorService.paypal)
        case Some(bank) if bank.name.equals("Stripe") =>
          ZIO.accessM[PaymentProcessorService](_.get.processing(process.getRecord_ID)).provideSomeLayer(processServices >>> PaymentProcessorService.stripe)
        case Some(_) =>
          ZIO.fail(new AdempiereException("@OnlinePaymentFailed@ @C_Bank@ @NotFound@ @To@ Stripe or PayPal"))
      }
    } yield result.getOrElse(""))


    val processRepository =
      PaymentProcessorRepository.live ++
        SecurityService.live ++
        PaymentRepository.live ++
        BankAccountRepository.live ++
        BankRepository.live ++
        PartnerRepository.live ++
        PartnerBankAccountRepository.live ++
        CurrencyRepository.live ++
        UserRepository.live

    val result = Runtime.default.unsafeRun(program.provideLayer(processRepository).catchAll(exception => {
      ZIO.effectTotal(addLog(exception.getMessage)) *> ZIO.fail(new AdempiereException(exception))
    }))
    s"@PaymentProcessed@ ${result.toString}"
  }
}

object PaymentOnlineService {

  def main(args: Array[String]): Unit = {
    Adempiere.startup(false)
    Ini.setProperty(Ini.P_UID, "GardenAdmin")
    Ini.setProperty(Ini.P_PWD, "GardenAdmin")
    Ini.setProperty(Ini.P_ROLE, "GardenWorld Admin")
    Ini.setProperty(Ini.P_CLIENT, "GardenWorld")
    Ini.setProperty(Ini.P_ORG, "HQ")
    Ini.setProperty(Ini.P_WAREHOUSE, "HQ Warehouse")
    Ini.setProperty(Ini.P_LANGUAGE, "English")
    val login = new Login(Env.getCtx)
    login.batchLogin
    CLogMgt.setLevel(Level.SEVERE)
    val processInfo = ProcessBuilder.create(Env.getCtx)
      .process(PaymentOnlineAbstract.getProcessId)
      .withTitle(PaymentOnlineAbstract.getProcessName)
      .withRecordId(I_C_Payment.Table_ID, 1000043)
      .withoutTransactionClose
      .execute(null)

    if (processInfo.isError) throw new exceptions.AdempiereException(processInfo.getSummary) else println(processInfo.getSummary)

  }

}