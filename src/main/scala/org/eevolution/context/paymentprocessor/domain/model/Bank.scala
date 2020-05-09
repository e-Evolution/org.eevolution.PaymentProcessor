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

import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage._

/**
  * Bank Entity
  *
  * @param bankId
  * @param uuid
  * @param tenantId
  * @param organizationId
  * @param isActive
  * @param created
  * @param createdBy
  * @param updated
  * @param updatedBy
  * @param name
  * @param bankType
  * @param partnerId
  * @param locationId
  * @param description
  * @param isOwnBank
  * @param routingNo
  * @param swiftCode
  */
case class Bank(bankId: Id,
                uuid: String,
                tenantId: TableDirect,
                organizationId: TableDirect = 0,
                isActive: YesNo = true,
                created: DateTime = LocalDateTime.now,
                createdBy: Table,
                updated: DateTime = LocalDateTime.now,
                updatedBy: Table,
                name: String,
                bankType: String,
                partnerId: TableDirect,
                locationId: TableDirect,
                description: String,
                isOwnBank: YesNo = true,
                routingNo: String,
                swiftCode: String
               )
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_Bank"
  override val identifier: String = "C_Bank_ID"
}

object Bank {

  def create(bankId: Id,
             uuid: String,
             tenantId: TableDirect,
             organizationId: TableDirect = 0,
             isActive: YesNo = true,
             created: DateTime = LocalDateTime.now,
             createdBy: Table,
             updated: DateTime = LocalDateTime.now,
             updatedBy: Table,
             name: String,
             bankType: String,
             partnerId: TableDirect,
             locationId: TableDirect,
             description: String,
             isOwnBank: YesNo = true,
             routingNo: String,
             swiftCode: String
            ): Bank =
    Bank(
      bankId,
      uuid,
      tenantId,
      organizationId,
      isActive,
      created,
      createdBy,
      updated,
      updatedBy,
      name,
      bankType,
      partnerId,
      locationId,
      description,
      isOwnBank,
      routingNo,
      swiftCode
    )
}

