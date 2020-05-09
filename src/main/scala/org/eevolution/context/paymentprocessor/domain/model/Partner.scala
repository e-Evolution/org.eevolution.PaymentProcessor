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
  * Partner Entity
  *
  * @param partnerId
  * @param uuid
  * @param tenantId
  * @param organizationId
  * @param isActive
  * @param created
  * @param createdBy
  * @param updated
  * @param updatedBy
  * @param value
  * @param name
  * @param partnerGroupId
  * @param accountTypeId
  * @param industryTypeId
  * @param salesGroupId
  * @param segmentId
  * @param description
  * @param isCustomer
  * @param isEmployee
  * @param isVendor
  */
case class Partner(partnerId: Id,
                   uuid: String,
                   tenantId: TableDirect,
                   organizationId: TableDirect = 0,
                   isActive: YesNo = true,
                   created: DateTime = LocalDateTime.now,
                   createdBy: Table,
                   updated: DateTime = LocalDateTime.now,
                   updatedBy: Table,
                   value: String,
                   name: String,
                   partnerGroupId: TableDirect,
                   accountTypeId: TableDirect,
                   industryTypeId: TableDirect,
                   salesGroupId: TableDirect,
                   segmentId: TableDirect,
                   description: String,
                   isCustomer: YesNo = true,
                   isEmployee: YesNo = false,
                   isVendor: YesNo = false
                  )
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_BPartner"
  override val identifier: String = "C_BPartner_ID"
}

object Partner {
  def create(partnerId: Id,
             uuid: String,
             tenantId: TableDirect,
             organizationId: TableDirect = 0,
             isActive: YesNo = true,
             created: DateTime = LocalDateTime.now,
             createdBy: Table,
             updated: DateTime = LocalDateTime.now,
             updatedBy: Table,
             value: String,
             name: String,
             partnerGroupId: TableDirect,
             accountTypeId: TableDirect,
             industryTypeId: TableDirect,
             salesGroupId: TableDirect,
             segmentId: TableDirect,
             description: String,
             isCustomer: YesNo = true,
             isEmployee: YesNo = false,
             isVendor: YesNo = false
            ): Partner =
    Partner(
      partnerId,
      uuid,
      tenantId,
      organizationId,
      isActive,
      created,
      createdBy,
      updated,
      updatedBy,
      value,
      name,
      partnerGroupId,
      accountTypeId,
      industryTypeId,
      salesGroupId,
      segmentId,
      description,
      isCustomer,
      isEmployee,
      isVendor
    )
}