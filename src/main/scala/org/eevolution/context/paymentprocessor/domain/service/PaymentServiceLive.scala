package org.eevolution.context.paymentprocessor.domain.service

import java.time.Instant

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository
import org.eevolution.context.paymentprocessor.api.service.PaymentService
import org.eevolution.context.paymentprocessor.domain.factory.PaymentBuilder
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage._
import org.eevolution.context.paymentprocessor.domain.valueobject.PaymentTenderType
import zio.RIO

case class PaymentServiceLive( paymentRepository: PaymentRepository.Service) extends PaymentService.Service {
 /* override def getById(id: Id): ZIO[Context, Throwable, Payment] = paymentRepository.getById(id)

  override def createPayment(documentType: DocumentType,
                             description: String,
                             bankAccount: BankAccount,
                             partner: Partner,
                             currency: Currency): ZIO[Context, Any, Payment] =  for {
      context <- contextService.getContext
      trx <- contextService.getTransaction
      tenant <- contextService.getTenant
      organization <-contextService.getOrganization
      payment <- PaymentBuilder()
        .withDocumentType(documentType)
        .withPartner(partner)
        .withBankAccount(bankAccount)
        .withDateTrx(Instant.now())
        .withDateAccount(Instant.now())
        .withCurrency(currency)
        .withTenderType(PaymentTenderType.Check.value)
        .withPayAmount(0)
        .build()
    } yield payment

  override def createPaymentWithCreditCard(documentType: DocumentType,
                                           description: String,
                                           bankAccount: BankAccount,
                                           partner: Partner,
                                           currency: Currency,
                                           creditCardType: List,
                                           creditCard: String,
                                           creditCardNumber: String,
                                           creditCardExpMM: Int,
                                           creditCardExpYY: Int,
                                           verificationCode: String): ZIO[Context, Any, Payment] =
    ZIO.accessM(environment =>
      for {
        context <- contextService.getContext
        trx <- contextService.getTransaction
        tenant <- contextService.getTenant
        organization <-contextService.getOrganization
        payment <- PaymentBuilder()
          .withDocumentType(documentType)
          .withDescription(description)
          .withPartner(partner)
          .withBankAccount(bankAccount)
          .withDateTrx(Instant.now())
          .withDateAccount(Instant.now())
          .withCurrency(currency)
          .withTenderType(PaymentTenderType.CreditCard.value)
          .withTrxType(PaymentTrxType.Sales.value)
          .withCreditCardType(creditCardType)
          .withCreditCardNumber(creditCardNumber)
          .withCreditCardExpMM(creditCardExpMM)
          .withCreditCardExpYY(creditCardExpYY)
          .withVerificationCode(verificationCode)
          .withPayAmount(0)
          .build()
      } yield payment
    )

  override def save(payment: Payment): ZIO[Context, Throwable, Payment] = ???
}
*/
  override def getById(id: Id): RIO[Any, Option[Payment]] = paymentRepository.getById(id)

  override def save(payment: Payment): RIO[Any,  Option[Payment]] = ???

  override def createPayment(documentType: DocumentType, description: String, bankAccount: BankAccount, partner: Partner, currency: Currency): RIO[Any,  Option[Payment]] = for {
    //context <- contextService.getContext
    //trx <- contextService.getTransaction
    //tenant <- contextService.getTenant
    //organization <- contextService.getOrganization
    payment <- PaymentBuilder()
      .withDocumentType(documentType)
      .withPartner(partner)
      .withBankAccount(bankAccount)
      .withDateTrx(Instant.now())
      .withDateAccount(Instant.now())
      .withCurrency(currency)
      .withTenderType(PaymentTenderType.Check.value)
      .withPayAmount(0)
      .build()
  } yield payment

  override def createPaymentWithCreditCard(documentType: DocumentType, description: String, bankAccount: BankAccount, partner: Partner, currency: Currency, creditCardType: ubiquitouslanguage.List, creditCard: String, creditCardNumber: String, creditCardExpMM: Int, creditCardExpYY: Int, verificationCode: String): RIO[Any,  Option[Payment]] = ???
}
