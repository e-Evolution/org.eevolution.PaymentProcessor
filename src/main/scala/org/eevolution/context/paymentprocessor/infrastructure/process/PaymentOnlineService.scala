package org.eevolution.context.paymentprocessor.infrastructure.process


import java.util.logging.Level

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
import org.eevolution.context.paymentprocessor.api.repository.{PaymentProcessorRepository, _}
import org.eevolution.context.paymentprocessor.api.service.BankAccountService.BankAccountService
import org.eevolution.context.paymentprocessor.api.service.BankService.BankService
import org.eevolution.context.paymentprocessor.api.service.PaymentProcessorService.PaymentProcessorService
import org.eevolution.context.paymentprocessor.api.service.PaymentService.PaymentService
import org.eevolution.context.paymentprocessor.api.service._
import org.eevolution.service.dsl.ProcessBuilder
import zio.console.Console
import zio.{Ref, Runtime, ZEnv, ZIO, console}

class PaymentOnlineService extends PaymentOnlineAbstract {

  override def prepare(): Unit = super.prepare()

  override def doIt(): String = {
    type processEnvironment = Console with PaymentProcessorRepository with PaymentRepository with BankAccountRepository with BankRepository with PartnerRepository with PartnerBankAccountRepository with CurrencyRepository  with UserRepository
    def program: ZIO[processEnvironment, Throwable, String] = for {
      _ <- console.putStr(s"Starting Program")
      process <- Ref.make(this).flatMap(reference => reference.get)
      payment <-  ZIO.accessM[PaymentService](_.get.getById(process.getRecord_ID)).provideSomeLayer(PaymentService.live)
      bankAccount <-  ZIO.accessM[BankAccountService](_.get.getById(payment.get.bankAccountId)).provideSomeLayer(BankAccountService.live)
      bank <-  ZIO.accessM[BankService](_.get.getById(bankAccount.get.bankId)).provideSomeLayer(BankService.live)
      result <-  bank match {
        case Some(bank) if bank.equals("PayPal") =>
          ZIO.accessM[PaymentProcessorService](_.get.processing(process.getRecord_ID)).provideSomeLayer(PaymentProcessorRepository.live ++ PaymentService.live ++ BankAccountService.live ++ BankService.live ++ PartnerService.live ++ PartnerBankAccountService.live ++ CurrencyService.live ++ UserService.live >>> PaymentProcessorService.paypal)
        case Some(bank) if bank.equals("Stripe") =>
          ZIO.accessM[PaymentProcessorService](_.get.processing(process.getRecord_ID)).provideSomeLayer(PaymentProcessorRepository.live ++ PaymentService.live ++ BankAccountService.live ++ BankService.live ++ PartnerService.live ++ PartnerBankAccountService.live ++ CurrencyService.live ++ UserService.live >>> PaymentProcessorService.stripe)
        case Some(_) => console.putStr(s"Procesador de pagos")
      }
      _ <- console.putStr(s"End Program")
    } yield process.getName

    val result = Runtime.default.unsafeRun(program.provideLayer(ZEnv.live ++ PaymentProcessorRepository.live ++ PaymentRepository.live ++ BankAccountRepository.live ++ BankRepository.live ++  PartnerRepository.live ++ PartnerBankAccountRepository.live ++ CurrencyRepository.live ++ UserRepository.live))
    s"@Ok@"
  }
}

object  PaymentOnlineService {

  def main(args: Array[String]): Unit = {
    Adempiere.startup(false)
    Ini.setProperty(Ini.P_UID, "SuperUser")
    Ini.setProperty(Ini.P_PWD, "System")
    Ini.setProperty(Ini.P_ROLE, "Pied A Terre, Inc Admin")
    Ini.setProperty(Ini.P_CLIENT, "Pied A Terre, Inc")
    Ini.setProperty(Ini.P_ORG, "Pied A Terre")
    Ini.setProperty(Ini.P_WAREHOUSE, "Estándar")
    Ini.setProperty(Ini.P_LANGUAGE, "English")
    // Ini.setProperty(Ini.P_PRINTER,"MyPrinter");
    val login = new Login(Env.getCtx)
    login.batchLogin
    CLogMgt.setLevel(Level.SEVERE)
    val processInfo = ProcessBuilder.create(Env.getCtx)
      .process(PaymentOnlineAbstract.getProcessId)
      .withTitle(PaymentOnlineAbstract.getProcessName)
      .withRecordId(I_C_Payment.Table_ID , 1000005)
      .withoutTransactionClose
      .execute(null)
  }

}