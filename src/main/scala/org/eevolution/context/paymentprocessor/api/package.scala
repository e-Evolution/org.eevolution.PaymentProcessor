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

package org.eevolution.context.paymentprocessor

import java.time.Instant

import org.compiere.model.PO

/**
  * Package Object for Ubiquitous Language a language structured around the domain model
  * and used by all team members to connect all the activities of the team with the software.
  */
package object UbiquitousLanguage {
  type Tenant = domain.model.Tenant
  type Organization = domain.model.Organization
  type User = domain.model.User
  type Currency = domain.model.Currency
  type Bank = domain.model.Bank
  type BankAccount = domain.model.BankAccount
  type PartnerBankAccount = domain.model.PartnerBankAccount
  type Payment = domain.model.Payment
  type PaymentProcessor = domain.model.PaymentProcessor
  type Partner = domain.model.Partner
  type DocumentType =  domain.model.DocumentType

  type Quantity = BigDecimal
  type Amount = BigDecimal
  type Id = Int
  type TableDirect = Int
  type Table = Int
  type Search = Int
  type List = String
  type Number = BigDecimal
  type Integer = Int
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