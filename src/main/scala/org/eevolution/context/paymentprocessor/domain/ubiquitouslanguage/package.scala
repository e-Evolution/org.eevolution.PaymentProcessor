package org.eevolution.context.paymentprocessor.domain

import java.time.Instant

import org.compiere.model.PO



package object ubiquitouslanguage {
  type Tenant = model.Tenant
  type Organization = model.Organization
  type User = model.User
  type Currency =model.Currency
  type Bank = model.Bank
  type BankAccount = model.BankAccount
  type PartnerBankAccount = model.PartnerBankAccount
  type Payment =model.Payment
  type PaymentProcessor = model.PaymentProcessor
  type Partner = model.Partner
  type DocumentType =  model.DocumentType

  type Quantity = BigDecimal
  type Amount = BigDecimal
  type Id = Int
  type TableDirect = Int
  type Table = Int
  type Search = Int
  type List = String
  type Number = BigDecimal
  type YesNo = Boolean
  type Button = String
  type Yes = true
  type No = false
  type DateTime = Instant
  type Date = Instant
  type Text = String
  type Domain = PO


  sealed trait Maybe
  sealed trait Required extends Maybe
  sealed trait Once extends Maybe

}
