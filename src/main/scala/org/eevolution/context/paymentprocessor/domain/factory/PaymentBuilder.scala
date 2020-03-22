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


package org.eevolution.context.paymentprocessor.domain.factory

import java.time.Instant

import org.eevolution.context.paymentprocessor.UbiquitousLanguage
import org.eevolution.context.paymentprocessor.UbiquitousLanguage._
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.domain.model.Payment
import org.eevolution.context.paymentprocessor.domain.valueobject.{DocumentStatus, PaymentTenderType, PaymentTrxType}
import zio.ZIO

/**
  * Factory to Create Payment Instance
  */
object PaymentBuilder {

  def apply() = new Builder[Required, Maybe, Required, Required, Required, Required, Required, Required, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe]()

  case class Builder
  [
    WithDocumentTypeTracking <: Maybe,
    WithDescriptionTracking <: Maybe,
    WithPartnerTracking <: Maybe,
    WithBankAccountTracking <: Maybe,
    WithDateTrxTracking <: Maybe,
    WithDateAccountTracking <: Maybe,
    WithCurrencyTracking <: Maybe,
    WithTenderType <: Maybe,
    WithTrxType <: Maybe,
    WithOnlineProcessing <: Maybe,
    WithCreditCardType <: Maybe,
    WithCreditCardExpMM <: Maybe,
    WithCreditCardExpYY <: Maybe,
    WithCreditCardNumber <: Maybe,
    WithCreditCardVerificationCode <: Maybe,
    WithPayAmt <: Maybe,
    WithWriteOffAmount <: Maybe,
    WithDiscountAmount <: Maybe,
    WithPrePayment <: Maybe
  ](
     maybeDocumentType: Option[DocumentType] = None,
     maybeDescription: Option[String] = None,
     maybePartner: Option[Partner] = None,
     maybeBankAccount: Option[BankAccount] = None,
     maybeDateTrx: Option[Date] = None,
     maybeDateAccount: Option[Date] = None,
     maybeCurrency: Option[Currency] = None,
     maybeTenderType: Option[String] = Some(PaymentTenderType.Check.value),
     maybeTrxType: Option[String] = Some(PaymentTrxType.Sales.value),
     maybeOnlineProcessing: Option[String] = None,
     maybeCreditCardType: Option[List] = None,
     maybeCreditCardExpMM: Option[Int] = None,
     maybeCreditCardExpYY: Option[Int] = None,
     maybeCreditCardNumber: Option[String] = None,
     maybeCreditCardVerificationCode: Option[String] = None,
     maybePayAmount: Option[Amount] = Some(BigDecimal.apply(0)),
     maybeWriteOffAmount: Option[Amount] = Some(BigDecimal.apply(0)),
     maybeDiscountAmount: Option[Amount] = Some(BigDecimal.apply(0)),
     maybeIsPrePayment: Option[YesNo] = Some(false)
   ) {

    type IsOnce[T] = =:=[T, Once]
    type IsMandatory[T] = =:=[T, Required]

    def withDocumentType[DocumentType <: WithPartnerTracking : IsMandatory](dt: UbiquitousLanguage.DocumentType) =
      copy[
        Once,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeDocumentType = Some(dt))

    def withDescription[Description <: WithDescriptionTracking : IsMandatory](d: String) =
      copy[
        WithDocumentTypeTracking,
        Once,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeDescription = Some(d))

    def withPartner[Partner <: WithPartnerTracking : IsMandatory](p: UbiquitousLanguage.Partner) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        Once,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybePartner = Some(p))

    def withBankAccount[BankAccount <: WithBankAccountTracking : IsMandatory](ba: UbiquitousLanguage.BankAccount) =
      copy[
        WithDocumentTypeTracking,
        WithDocumentTypeTracking,
        WithPartnerTracking,
        Once,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment
      ](maybeBankAccount = Some(ba))

    def withDateTrx[DateTrx <: WithDateTrxTracking : IsMandatory](dt: Date) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        Once,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment
      ](maybeDateTrx = Some(dt))

    def withDateAccount[DateAccount <: WithDateAccountTracking : IsMandatory](da: Date) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        Once,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment
      ](maybeDateAccount = Some(da))

    def withCurrency[Currency <: WithCurrencyTracking : IsMandatory](c: UbiquitousLanguage.Currency) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        Once,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment
      ](maybeCurrency = Some(c))

    def withTenderType[TenderType <: WithTenderType : IsMandatory](tt: String) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        Once,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeTenderType = Some(tt))

    def withTrxType[TrxType <: WithTrxType : IsMandatory](tt: List) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        Once,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeTrxType = Some(tt))

    def withCreditCardType[CreditCardType <: WithCreditCardType : IsMandatory](cct: List) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        Once,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeCreditCardType = Some(cct))

    def withCreditCardExpMM[CreditCardExpMM <: WithCreditCardExpMM : IsMandatory](ccemm: Int) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        Once,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeCreditCardExpMM = Some(ccemm))

    def withCreditCardExpYY[CreditCardExpYY <: WithCreditCardExpYY : IsMandatory](cceyy: Int) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        Once,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeCreditCardExpYY = Some(cceyy))


    def withCreditCardNumber[CreditCardNumber <: WithCreditCardNumber : IsMandatory](ccn: String) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        Once,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeCreditCardNumber = Some(ccn))


    def withVerificationCode[VerificationCode <: WithCreditCardVerificationCode : IsMandatory](vc: String) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        Once,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment](maybeCreditCardVerificationCode = Some(vc))


    def withPayAmount[PayAmt <: WithPayAmt : IsMandatory](pa: Amount) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        Once,
        WithWriteOffAmount,
        WithDiscountAmount,
        WithPrePayment
      ](maybePayAmount = Some(pa))


    def withWriteOffAmount[WriteOffAmount <: WithWriteOffAmount : IsMandatory](wa: Amount) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        Once,
        WithDiscountAmount,
        WithPrePayment](maybeWriteOffAmount = Some(wa))


    def withDiscountAmount[DiscountAmount <: WithDiscountAmount : IsMandatory](da: Amount) =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        Once,
        WithPrePayment](maybeDiscountAmount = Some(da))

    def asPrePayment[IsPrePayment <: WithPrePayment : IsMandatory]() =
      copy[
        WithDocumentTypeTracking,
        WithDescriptionTracking,
        WithPartnerTracking,
        WithBankAccountTracking,
        WithDateTrxTracking,
        WithDateAccountTracking,
        WithCurrencyTracking,
        WithTenderType,
        WithTrxType,
        WithOnlineProcessing,
        WithCreditCardType,
        WithCreditCardExpMM,
        WithCreditCardExpYY,
        WithCreditCardNumber,
        WithCreditCardVerificationCode,
        WithPayAmt,
        WithWriteOffAmount,
        WithDiscountAmount,
        Once
      ](maybeIsPrePayment = Some(true))

    def build[
      DocumentType <: WithDocumentTypeTracking : IsOnce,
      Description <: WithDescriptionTracking : IsOnce,
      Partner <: WithPartnerTracking : IsOnce,
      BankAccount <: WithBankAccountTracking : IsOnce,
      DateTrx <: WithDateTrxTracking : IsOnce,
      DateAccount <: WithDateAccountTracking : IsOnce,
      Currency <: WithCurrencyTracking : IsOnce,
      TenderType <: WithTenderType : IsOnce,
      TrxType <: WithTrxType : IsOnce,
      OnlineProcessing <: WithOnlineProcessing : IsOnce,
      CreditCardType <: WithCreditCardType,
      CreditCardExpMM <: WithCreditCardExpMM,
      CreditCardExpYY <: WithCreditCardExpYY,
      CreditCardNumber <: WithCreditCardNumber,
      CreditCardVerificationCode <: WithCreditCardVerificationCode,
      PayAmt <: WithPayAmt : IsOnce,
      WriteOffAmount <: WithWriteOffAmount : IsOnce,
      DiscountAmount <: WithDiscountAmount : IsOnce,
    ](): ZIO[ContextEnvironment, Any, Payment] = ZIO.accessM {
      environment =>
        for {
          tenant <- environment.execution.getTenant
          organization <- environment.execution.getOrganization
          user <- environment.execution.getUser
          description <- ZIO.fromOption(maybeDescription)
          bankAccount <- ZIO.fromOption(maybeBankAccount)
          dateTrx <- ZIO.fromOption(maybeDateTrx)
          dateAccount <- ZIO.fromOption(maybeDateAccount)
          currency <- ZIO.fromOption(maybeCurrency)
          payAmount <- ZIO.fromOption(maybePayAmount)
          writeOffAmount <- ZIO.fromOption(maybeWriteOffAmount)
          discountAmount <- ZIO.fromOption(maybeDiscountAmount)
          documentType <- ZIO.fromOption(maybeDocumentType)
          partner <- ZIO.fromOption(maybePartner)
          tenderType <- ZIO.fromOption(maybeTenderType)
          transactionType <- ZIO.fromOption(maybeTrxType)
          onlineProcessing <- ZIO.fromOption(maybeOnlineProcessing)
          creditCardType <- ZIO.fromOption(maybeCreditCardType)
          creditCardExpMM <- ZIO.fromOption(maybeCreditCardExpMM)
          creditCardExpYY <- ZIO.fromOption(maybeCreditCardExpYY)
          creditCardNumber <- ZIO.fromOption(maybeCreditCardNumber)
          creditCardVerificationCode <- ZIO.fromOption(maybeCreditCardVerificationCode)
          payment <- ZIO.effectTotal(
            Payment.create(
              0,
              "",
              tenant.Id,
              organization.Id,
              true,
              Instant.now,
              user.Id,
              Instant.now,
              user.Id,
              "",
              "",
              "",
              0,
              "",
              "",
              "",
              "",
              "",
              "",
              "",
              0,
              bankAccount.Id,
              partner.Id,
              0,
              0,
              0,
              0,
              0,
              currency.Id,
              documentType.Id,
              0.0,
              "",
              0,
              0,
              0,
              0,
              0,
              creditCardType,
              creditCardExpMM,
              creditCardExpYY,
              creditCardNumber,
              creditCardVerificationCode,
              dateTrx,
              dateAccount,
              description,
              discountAmount,
              "",
              DocumentStatus.Drafted.value,
              "",
              false,
              false,
              false,
              false,
              true,
              false,
              false,
              false,
              false,
              false,
              "",
              onlineProcessing,
              "",
              0.0,
              payAmount,
              "",
              "N",
              false,
              0,
              "N",
              "",
              "",
              "",
              "",
              false,
              0,
              0,
              0,
              "",
              "",
              "",
              "",
              "",
              "",
              0,
              "",
              0.0,
              tenderType,
              transactionType,
              0,
              0,
              0,
              0,
              "",
              writeOffAmount))
        } yield payment
    }
  }

}