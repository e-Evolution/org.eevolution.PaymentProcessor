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
import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Id, List, Maybe, Once, Required, User, YesNo}
import org.eevolution.context.paymentprocessor.api.repository.Context.ContextEnvironment
import org.eevolution.context.paymentprocessor.domain.model.PartnerBankAccount
import zio.ZIO

object PartnerBankAccountBuilder {

  def apply() = new Builder[Required, Required, Required, Required, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe, Maybe]()

  case class Builder[
    WithAccountNameTracking <: Maybe,
    WithPartnerIdTracking <: Maybe,
    WithBankIdTracking <: Maybe,
    WithIsACHTracking <: Maybe,
    WithIsPayrollAccount <: Maybe,
    WithAccountNoTracking <: Maybe,
    WithAccountEMailTracking <: Maybe,
    WithAccountStreetTracking <: Maybe,
    WithAccountCountryTracking <: Maybe,
    WithAccountCityTracking <: Maybe,
    WithAccountStateTracking <: Maybe,
    WithAccountZipTracking <: Maybe,
    WithBankAccountTypeTracking <: Maybe,
    WithPartnerBankAccountUseTracking <: Maybe,
    WithAccountDriverLicenseTracking <: Maybe,
    WithAccountSocialSecurityNoTracking <: Maybe,
    WithUserTracking <: Maybe,
    WithCreditCardTypeTracking <: Maybe,
    WithCreditCardExpMMTracking <: Maybe,
    WithCreditCardExpYYTracking <: Maybe,
    WithCreditCardNumberTracking <: Maybe,
    WithCreditCardVerificationCodeTracking <: Maybe,
    WithIBANTracking <: Maybe,
    WithRoutingNoTracking <: Maybe,
  ](
     maybeAccountName: Option[String] = None,
     maybePartnerId: Option[Id] = None,
     maybeBankId: Option[Id] = None,
     maybeIsACH: Option[YesNo] = Some(false),
     maybeIsPayrollAccount: Option[YesNo] = Some(false),
     maybeAccountNo: Option[String] = None,
     maybeAccountEMail: Option[String] = None,
     maybeAccountStreet: Option[String] = None,
     maybeAccountCountry: Option[String] = None,
     maybeAccountCity: Option[String] = None,
     maybeAccountState: Option[String] = None,
     maybeAccountZip: Option[String] = None,
     maybeBankAccountType: Option[List] = None,
     maybePartnerIdBankAccountUse: Option[List] = None,
     maybeAccountDriverLicense: Option[List] = None,
     maybeAccountSocialSecurityNo: Option[List] = None,
     maybeUser: Option[User] = None,
     maybeCreditCardType: Option[String] = None,
     maybeCreditCardExpMM: Option[Int] = None,
     maybeCreditCardExpYY: Option[Int] = None,
     maybeCreditCardNumber: Option[String] = None,
     maybeCreditCardVerificationCode: Option[String] = None,
     maybeIBAN: Option[String] = None,
     maybeRoutingNo: Option[String] = None
   ) {

    type IsOnce[T] = =:=[T, Once]
    type IsMandatory[T] = =:=[T, Required]

    def WithAccountName[AccountName <: WithAccountNameTracking : IsMandatory](accountName: String) =
      copy[Once,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountName = Some(accountName))

    def WithPartnerId[Partner <: WithPartnerIdTracking : IsMandatory](partnerId: UbiquitousLanguage.Id) =
      copy[
        WithAccountNameTracking,
        Once,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybePartnerId = Some(partnerId))

    def WithBank[Bank <: WithBankIdTracking : IsMandatory](id: UbiquitousLanguage.Id) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        Once,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeBankId = Some(id))

    def IsACH[IsACH <: WithIsACHTracking : IsMandatory](isACH: UbiquitousLanguage.YesNo) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        Once,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeIsACH = Some(isACH))

    def WithAccountNo[AccountNo <: WithAccountNoTracking : IsMandatory](accountNo: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        Once,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountNo = Some(accountNo))

    def WithAccountEMail[AccountEMail <: WithAccountEMailTracking : IsMandatory](emil: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        Once,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountEMail = Some(emil))

    def WithAccountStreet[AccountStreet <: WithAccountStreetTracking : IsMandatory](accountStreet: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        Once,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountStreet = Some(accountStreet))

    def WithAccountCountry[AccountCountry <: WithAccountCountryTracking : IsMandatory](accountCountry: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        Once,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountCountry = Some(accountCountry))

    def WithAccountCity[AccountCity <: WithAccountCityTracking : IsMandatory](accountCity: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        Once,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountCity = Some(accountCity))

    def WithAccountState[AccountState <: WithAccountStateTracking : IsMandatory](accountState: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        Once,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountState = Some(accountState))

    def WithAccountZip[AccountZip <: WithAccountZipTracking : IsMandatory](accountZip: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        Once,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountZip = Some(accountZip))

    def WithBankAccountType[BankAccountType <: WithBankAccountTypeTracking : IsMandatory](bankAccountType: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        Once,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeBankAccountType = Some(bankAccountType))

    def WithPartnerBankAccountUse[PartnerBankAccountUse <: WithPartnerBankAccountUseTracking : IsMandatory](partnerBankAccountUse: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        Once,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountEMail = Some(partnerBankAccountUse))

    def WithAccountDriverLicense[AccountDriverLicense <: WithAccountDriverLicenseTracking : IsMandatory](accountDriverLicense: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        Once,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountDriverLicense = Some(accountDriverLicense))

    def WithAccountSocialSecurityNo[AccountSocialSecurityNo <: WithAccountSocialSecurityNoTracking : IsMandatory](accountSocialSecurityNo: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        Once,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeAccountSocialSecurityNo = Some(accountSocialSecurityNo))

    def WithUser[User <: WithUserTracking : IsMandatory](user: UbiquitousLanguage.User) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        Once,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeUser = Some(user))

    def WithCreditCardType[CreditCardType <: WithCreditCardTypeTracking : IsMandatory](creditCardType: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        Once,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeCreditCardType = Some(creditCardType))

    def WithCreditCardExpMM[CreditCardExpMM <: WithCreditCardExpMMTracking : IsMandatory](creditCardExpMM: Int) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        Once,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeCreditCardExpMM = Some(creditCardExpMM))

    def WithCreditCardExpYY[CreditCardExpYY <: WithCreditCardExpYYTracking : IsMandatory](creditCardExpYY: Int) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        Once,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeCreditCardExpYY = Some(creditCardExpYY))

    def WithCreditCardNumber[CreditCardNumber <: WithCreditCardNumberTracking : IsMandatory](creditCardNumber: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        Once,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        WithRoutingNoTracking](maybeCreditCardNumber = Some(creditCardNumber))

    def WithCreditCardVerificationCode[CreditCardVerificationCode <: WithCreditCardVerificationCodeTracking : IsMandatory](creditCardVerificationCode: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        Once,
        WithIBANTracking,
        WithRoutingNoTracking](maybeCreditCardVerificationCode = Some(creditCardVerificationCode))

    def WithIBAN[IsIBAN <: WithIBANTracking : IsMandatory](iban: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        Once,
        WithRoutingNoTracking](maybeIBAN = Some(iban))

    def WithRoutingNo[RoutingNo <: WithRoutingNoTracking : IsMandatory](routingNo: String) =
      copy[
        WithAccountNameTracking,
        WithPartnerIdTracking,
        WithBankIdTracking,
        WithIsACHTracking,
        WithIsPayrollAccount,
        WithAccountNoTracking,
        WithAccountEMailTracking,
        WithAccountStreetTracking,
        WithAccountCountryTracking,
        WithAccountCityTracking,
        WithAccountStateTracking,
        WithAccountZipTracking,
        WithBankAccountTypeTracking,
        WithPartnerBankAccountUseTracking,
        WithAccountDriverLicenseTracking,
        WithAccountSocialSecurityNoTracking,
        WithUserTracking,
        WithCreditCardTypeTracking,
        WithCreditCardExpMMTracking,
        WithCreditCardExpYYTracking,
        WithCreditCardNumberTracking,
        WithCreditCardVerificationCodeTracking,
        WithIBANTracking,
        Once](maybeRoutingNo = Some(routingNo))

    def build[
      AccountName <: WithAccountNameTracking : IsOnce,
      PartnerId <: WithPartnerIdTracking : IsOnce,
      BankId <: WithBankIdTracking : IsOnce,
      IsACH <: WithIsACHTracking : IsOnce,
      IsPayroll <: WithIsPayrollAccount : IsOnce,
      AccountNo <: WithAccountNoTracking : IsOnce,
      AccountEMail <: WithAccountEMailTracking : IsOnce,
      AccountStreet <: WithAccountStreetTracking : IsOnce,
      AccountCountry <: WithAccountCountryTracking : IsOnce,
      AccountCity <: WithAccountCityTracking : IsOnce,
      AccountState <: WithAccountStateTracking : IsOnce,
      AccountZip <: WithAccountZipTracking : IsOnce,
      BankAccountType <: WithBankAccountTypeTracking : IsOnce,
      PartnerBankAccountUse <: WithPartnerBankAccountUseTracking : IsOnce,
      AccountDriverLicense <: WithAccountDriverLicenseTracking : IsOnce,
      AccountSocialSecurityNo <: WithAccountSocialSecurityNoTracking : IsOnce,
      User <: WithUserTracking : IsOnce,
      CreditCardType <: WithCreditCardTypeTracking : IsOnce,
      CreditCardExpMM <: WithCreditCardExpMMTracking : IsOnce,
      CreditCardExpYY <: WithCreditCardExpYYTracking : IsOnce,
      CreditCardNumber <: WithCreditCardNumberTracking : IsOnce,
      CreditCardVerificationCode <: WithCreditCardVerificationCodeTracking : IsOnce,
      IBAN <: WithIBANTracking : IsOnce,
      RoutingNo <: WithRoutingNoTracking : IsOnce
    ](): ZIO[ContextEnvironment, Any, PartnerBankAccount] = ZIO.accessM {
      environment =>
        for {
          tenant <- environment.execution.getTenant
          organization <- environment.execution.getOrganization
          user <- environment.execution.getUser
          accountName <- ZIO.fromOption(maybeAccountName)
          partnerId <- ZIO.fromOption(maybePartnerId)
          bankId <- ZIO.fromOption(maybeBankId)
          isACH <- ZIO.fromOption(maybeIsACH)
          isPayrollAccount <- ZIO.fromOption(maybeIsPayrollAccount)
          accountNo <- ZIO.fromOption(maybeAccountNo)
          accountEMail <- ZIO.fromOption(maybeAccountEMail)
          accountStreet <- ZIO.fromOption(maybeAccountStreet)
          accountCountry <- ZIO.fromOption(maybeAccountCountry)
          accountCity <- ZIO.fromOption(maybeAccountCity)
          accountState <- ZIO.fromOption(maybeAccountState)
          accountZip <- ZIO.fromOption(maybeAccountZip)
          bankAccountType <- ZIO.fromOption(maybeBankAccountType)
          partnerBankAccountUse <- ZIO.fromOption(maybePartnerIdBankAccountUse)
          accountDriverLicense <- ZIO.fromOption(maybeAccountDriverLicense)
          accountSocialSecurityNo <- ZIO.fromOption(maybeAccountSocialSecurityNo)
          user <- ZIO.fromOption(maybeUser)
          creditCardType <- ZIO.fromOption(maybeCreditCardType)
          creditCardExpMM <- ZIO.fromOption(maybeCreditCardExpMM)
          creditCardExpYY <- ZIO.fromOption(maybeCreditCardExpYY)
          creditCardNumber <- ZIO.fromOption(maybeCreditCardNumber)
          creditCardVerificationCode <- ZIO.fromOption(maybeCreditCardVerificationCode)
          iban <- ZIO.fromOption(maybeIBAN)
          routingNo <- ZIO.fromOption(maybeRoutingNo)
          partnerBankAccount <- ZIO.effectTotal(
            PartnerBankAccount.create(
              0,
              partnerId,
              bankId,
              "",
              tenant.Id,
              organization.Id,
              true,
              Instant.now,
              user.userId,
              Instant.now,
              user.userId,
              accountNo,
              accountName,
              accountCity,
              accountCountry,
              accountState,
              accountStreet,
              accountEMail,
              accountDriverLicense,
              accountSocialSecurityNo,
              accountZip,
              user.userId,
              bankAccountType,
              partnerBankAccountUse,
              creditCardExpMM,
              creditCardExpYY,
              creditCardNumber,
              creditCardType,
              creditCardVerificationCode,
              isACH,
              isPayrollAccount,
              "",
              "",
              routingNo,
              iban
            ))
        } yield partnerBankAccount
    }
  }

}
