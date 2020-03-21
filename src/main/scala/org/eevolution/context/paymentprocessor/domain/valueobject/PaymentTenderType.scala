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

package org.eevolution.context.paymentprocessor.domain.valueobject

/**
  * Value Object Payment Tender Type
  */
sealed trait PaymentTenderType {
  val id: Int
  val uuid: String
  val value: String
  val name: String
  val description: String
}

object PaymentTenderType {
  val ReferenceId = 214

  case object DirectDeposit extends PaymentTenderType {
    val id = 402
    val uuid = "a455e964-fb40-11e8-a479-7a0060f0aa01"
    val value = "A"
    val name = "Direct Deposit"
    val description = "ACH Automatic Clearing House"
  }

  case object CreditCard extends PaymentTenderType {
    val id = 400
    val uuid = "a4560412-fb40-11e8-a479-7a0060f0aa01"
    val value = "C"
    val name = "Credit Card"
    val description = ""
  }

  case object DirectDebit extends PaymentTenderType {
    val id = 651
    val uuid = "a455e964-fb40-11e8-a479-7a0060f0aa01"
    val value = "D"
    val name = "Direct Debit"
    val description = ""
  }

  case object Check extends PaymentTenderType {
    val id = 401
    val uuid = "a45605fc-fb40-11e8-a479-7a0060f0aa01"
    val value = "K"
    val name = "Direct Deposit"
    val description = ""
  }

  case object CreditMemo extends PaymentTenderType {
    val id = 54626
    val uuid = "a46773be-fb40-11e8-a479-7a0060f0aa01"
    val value = "M"
    val name = "Credit Memo"
    val description = ""
  }

  case object Account extends PaymentTenderType {
    val id = 53351
    val uuid = "a467758a-fb40-11e8-a479-7a0060f0aa01"
    val value = "T"
    val name = "Account"
    val description = ""
  }

}