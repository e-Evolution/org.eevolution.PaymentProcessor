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
  * Value Object Payment Trx Type
  */
sealed trait PaymentTrxType {
  val id: Int
  val uuid: String
  val value: String
  val name: String
  val description: String
}

object PaymentTrxType {

  val ReferenceId = 215

  case object Authorization extends PaymentTrxType {
    val id = 407
    val uuid = "a460d3a6-fb40-11e8-a479-7a0060f0aa01"
    val value = "A"
    val name = "Authorization"
    val description = ""
  }

  case object Credit extends PaymentTrxType {
    val id = 405
    val uuid = "a4560048-fb40-11e8-a479-7a0060f0aa01"
    val value = "C"
    val name = "Credit (Payment)"
    val description = ""
  }

  case object DelayedCapture extends PaymentTrxType {
    val id = 404
    val uuid = "a45600b6-fb40-11e8-a479-7a0060f0aa01"
    val value = "D";
    val name = "Delayed Capture";
    val description = ""
  }

  case object VoiceAuthorization extends PaymentTrxType {
    val id = 406
    val uuid = "a45600b6-fb40-11e8-a479-7a0060f0aa01"
    val value = "F";
    val name = "Voice Authorization";
    val description = ""
  }

  case object Sales extends PaymentTrxType {
    val id = 403
    val uuid = "a4560188-fb40-11e8-a479-7a0060f0aa01"
    val value = "S";
    val name = "Sales";
    val description = ""
  }

  case object Void extends PaymentTrxType {
    val id = 408
    val uuid = "a456021e-fb40-11e8-a479-7a0060f0aa01"
    val value = "V";
    val name = "Void";
    val description = ""
  }

}
