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
  * Value Object for Document Status
  */
sealed trait DocumentStatus {
  val id: Int
  val uuid: String
  val value: String
  val name: String
  val description: String
}

object DocumentStatus {
  val ReferenceId = 131
  val uuid = "a47d3dc0-fb40-11e8-a479-7a0060f0aa01"

  case object Unknown extends DocumentStatus {
    val id = 190
    val uuid = "a4560692-fb40-11e8-a479-7a0060f0aa01"
    val value = "??"
    val name = "Unknown"
    val description = ""
  }

  case object Closed extends DocumentStatus {
    val id = 177
    val uuid = "a455fe36-fb40-11e8-a479-7a0060f0aa01"
    val value = "CL"
    val name = "Closed"
    val description = ""
  }

  case object Completed extends DocumentStatus {
    val id = 165
    val uuid = "a455fea4-fb40-11e8-a479-7a0060f0aa01"
    val value = "CO"
    val name = "Completed"
    val description = ""
  }

  case object Drafted extends DocumentStatus {
    val id = 164
    val uuid = "a459d506-fb40-11e8-a479-7a0060f0aa01"
    val value = "DR"
    val name = "Drafted"
    val description = ""
  }

  case object Invalid extends DocumentStatus {
    val id = 173
    val uuid = "a455ff08-fb40-11e8-a479-7a0060f0aa01"
    val value = "IN"
    val name = "Invalid"
    val description = ""
  }

  case object InProgress extends DocumentStatus {
    val id = 341
    val uuid = "a456aec6-fb40-11e8-a479-7a0060f0aa01"
    val value = "IP"
    val name = "In Progress"
    val description = ""
  }

  case object NotApproved extends DocumentStatus {
    val id = 168
    val uuid = "a459d290-fb40-11e8-a479-7a0060f0aa01"
    val value = "NA"
    val name = "Not Approved"
    val description = ""
  }

  case object Reversed extends DocumentStatus {
    val id = 176
    val uuid = "a455ff76-fb40-11e8-a479-7a0060f0aa01"
    val value = "RE"
    val name = "Reversed"
    val description = ""
  }

  case object Voided extends DocumentStatus {
    val id = 172
    val uuid = "a455ffe4-fb40-11e8-a479-7a0060f0aa01"
    val value = "VO"
    val name = "Voided"
    val description = ""
  }

  case object WaitingConfirmation extends DocumentStatus {
    val id = 670
    val uuid = "a46776f2-fb40-11e8-a479-7a0060f0aa01"
    val value = "WC"
    val name = "Waiting Confirmation"
    val description = ""
  }

  case object WaitingPayment extends DocumentStatus {
    val id = 346
    val uuid = "a459d308-fb40-11e8-a479-7a0060f0aa01"
    val value = "WP"
    val name = "Waiting Payment"
    val description = ""
  }

}
