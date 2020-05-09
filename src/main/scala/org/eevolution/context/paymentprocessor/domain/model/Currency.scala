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

package org.eevolution.context.paymentprocessor.domain.model

import java.time.LocalDateTime

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{DateTime, Id, Number, Table, TableDirect, YesNo}

/**
  * Currency Entity
  *
  * @param currencyId  Currency ID
  * @param tenantId    Tenant ID
  * @param isActive    Is Active
  * @param created     Created
  * @param createdBy   Created By
  * @param updated     Updated
  * @param updatedBy   Updated By
  * @param description Description
  * @param uuid        UUID
  */
case class Currency(currencyId: Id
                    , tenantId: TableDirect
                    , organizationId: TableDirect = 0
                    , isActive: YesNo = true
                    , created: DateTime = LocalDateTime.now
                    , createdBy: Table
                    , updated: DateTime = LocalDateTime.now
                    , updatedBy: Table
                    , description: String
                    , costingPrecision: Id
                    , curSymbol: String
                    , emuEntryDate: String
                    , emuRate: Option[Number] = Some(BigDecimal(0))
                    , isEMUMember: YesNo = false
                    , isEuro: YesNo
                    , isoCode: String
                    , roundOffFactor: Option[Number] = Some(BigDecimal(0))
                    , stdPrecision: Id
                    , uuid: String) extends DomainModel

  with ActiveEnabled
  with Identifiable
  with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = currencyId

  override val entityName: String = "C_Currency"
  override val identifier: String = "C_Currency_ID"
}

object Currency {
  //implicit lazy val jsonFormat = Jsonx.formatCaseClass[Currency]
  def create(currencyId: Id, tenantId: TableDirect, organizationId: TableDirect = 0, isActive: YesNo = true, created: DateTime = LocalDateTime.now, createdBy: Table, updated: DateTime = LocalDateTime.now, updatedBy: Table, description: String, costingPrecision: Id, curSymbol: String, emuEntryDate: String, emuRate: Number, isEMUMember: YesNo = false, isEuro: YesNo, isoCode: String, roundOffFactor: Number, stdPrecision: Id, uuid: String): Currency = Currency(currencyId, tenantId, organizationId, isActive, created, createdBy, updated, updatedBy, description, costingPrecision, curSymbol, emuEntryDate, Option(emuRate), isEuro, isEuro, isoCode, Option(roundOffFactor), stdPrecision, uuid)
}

