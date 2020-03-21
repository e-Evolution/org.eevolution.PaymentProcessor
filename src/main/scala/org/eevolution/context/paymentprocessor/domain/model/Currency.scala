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

import java.time.Instant

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{DateTime, Id, Table, TableDirect, YesNo, Number}

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
  * @param value       Value
  * @param name        Name
  * @param description Description
  * @param uuid        UUID
  */
case class Currency(currencyId: Id,
                    tenantId: TableDirect,
                    organizationId: TableDirect = 0,
                    isActive: YesNo = true,
                    created: DateTime = Instant.now(),
                    createdBy: Table,
                    updated: DateTime = Instant.now(),
                    updatedBy: Table,
                    value: String,
                    name: String,
                    description: String,
                    costingPrecision: Int,
                    curSymbol : String,
                    emuEntryDate : String,
                    emuRate: Number,
                    isEMUMember : YesNo =  false,
                    isEuro : YesNo,
                    isoCode:String,
                    roundOffFactor: Number,
                    stdPrecision : Int,
                    uuid: String
                   ) extends DomainModel

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
  def create(currencyId: Id,
             tenantId: TableDirect,
             organizationId: TableDirect = 0,
             isActive: YesNo = true,
             created: DateTime = Instant.now(),
             createdBy: Table,
             updated: DateTime = Instant.now(),
             updatedBy: Table,
             value: String,
             name: String,
             description: String,
             costingPrecision: Int,
             curSymbol : String,
             emuEntryDate : String,
             emuRate: Number,
             isEMUMember : YesNo =  false,
             isEuro : YesNo,
             isoCode:String,
             roundOffFactor: Number,
             stdPrecision : Int,
             uuid: String) = Currency(currencyId, tenantId, organizationId, isActive, created, createdBy, updated, updatedBy, value, name, description, costingPrecision, curSymbol, emuEntryDate, emuRate, isEuro ,isEuro,isoCode,roundOffFactor ,stdPrecision,uuid)
}

