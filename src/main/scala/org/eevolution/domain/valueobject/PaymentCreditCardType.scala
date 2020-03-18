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

package org.eevolution.domain.valueobject

/**
  * Value Object Payment Credit Card Type
  */
sealed trait PaymentCreditCardType {
  val id: Int
  val uuid: String
  val value: String
  val name: String
  val description: String
}

object PaymentCreditCardType {
  val ReferenceId = 149

  case object Amex extends PaymentCreditCardType {
    val id = 210
    val uuid = "a4555814-fb40-11e8-a479-7a0060f0aa01"
    val value = "A"
    val name = "Amex"
    val description = ""
  }

  case object ATM extends PaymentCreditCardType {
    val id = 213
    val uuid = "a4555882-fb40-11e8-a479-7a0060f0aa01"
    val value = "A"
    val name = "ATM"
    val description = ""
  }

  case object Diners extends PaymentCreditCardType {
    val id = 417
    val uuid = "a4575f7e-fb40-11e8-a479-7a0060f0aa01"
    val value = "D"
    val name = "Diners"
    val description = ""
  }

  case object MasterCard extends PaymentCreditCardType {
    val id = 211
    val uuid = "a45563ae-fb40-11e8-a479-7a0060f0aa01"
    val value = "M"
    val name = "MasterCard"
    val description = ""
  }

  case object PurchaseCard extends PaymentCreditCardType {
    val id = 419
    val uuid = "a455cdbc-fb40-11e8-a479-7a0060f0aa01"
    val value = "P"
    val name = "Purchase Card"
    val description = ""
  }

  case object Visa extends PaymentCreditCardType {
    val id = 212
    val uuid = "a4556480-fb40-11e8-a479-7a0060f0aa01"
    val value = "V"
    val name = "Visa"
    val description = ""
  }

}