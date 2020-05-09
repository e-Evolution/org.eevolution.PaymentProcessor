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

package org.eevolution.context.paymentprocessor.domain

import java.time.{LocalDate, LocalDateTime}

import org.compiere.model.PO


package object ubiquitouslanguage {
  type Tenant = model.Tenant
  type Organization = model.Organization
  type User = model.User
  type Currency = model.Currency
  type Bank = model.Bank
  type BankAccount = model.BankAccount
  type PartnerBankAccount = model.PartnerBankAccount
  type Payment = model.Payment
  type PaymentProcessor = model.PaymentProcessor
  type Partner = model.Partner
  type DocumentType = model.DocumentType

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
  type DateTime = LocalDateTime
  type Date = LocalDate
  type Text = String
  type Domain = PO


  sealed trait Maybe

  sealed trait Required extends Maybe

  sealed trait Once extends Maybe

}
