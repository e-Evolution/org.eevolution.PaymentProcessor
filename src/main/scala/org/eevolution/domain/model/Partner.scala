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

package org.eevolution.domain.model

import java.time.Instant

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
case class Partner(partnerId: Int,
                   uuid: String,
                   tenantId: Int,
                   organizationId: Int = 0,
                   isActive: Boolean = true,
                   created: Instant = Instant.now,
                   createdBy: Int,
                   updated: Instant = Instant.now,
                   updatedBy: Int,
                   value: String,
                   name: String,
                   partnerGroupId: Int,
                   accountTypeId: Int,
                   industryTypeId: Int,
                   salesGroupId: Int,
                   segmentId: Int,
                   description: String,
                   isCustomer: Boolean = true,
                   isEmployee: Boolean = false,
                   isVendor: Boolean = false
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

  val EntityName = "C_BPartner_ID"
  val BankId = "C_BPartner_ID"
  val TenantId = "AD_Client_ID"
  val OrganizationId = "AD_Org_ID"
  val IsActive = "IsActive"
  val Created = "Created"
  val CreatedBy = "CreatedBy"
  val Updated = "Updated"
  val UpdatedBy = "UpdatedBy"
  val EntityId = "Ad_Table_ID"
  val Value = "Value"
  val Name = "Name"
  val UUID = "UUID"
  val PartnerId = "C_BPartner_ID"
  val PartnerGroupId = "C_BP_Group_ID"
  val AccountTypeId = "C_BP_AccountType_ID"
  val IndustryTypeId = "C_BP_IndustryType_ID"
  val SalesGroupId = "C_BP_SalesGroup_ID"
  val SegmentId = "C_BP_Segment_ID"
  val Description = "Description"
  val IsCustomer = "IsCustomer"
  val IsEmployee = "IsEmployee"
  val IsVendor = "IsVendor"


  def create(partnerId: Int,
             uuid: String,
             tenantId: Int,
             organizationId: Int = 0,
             isActive: Boolean = true,
             created: Instant = Instant.now,
             createdBy: Int,
             updated: Instant = Instant.now,
             updatedBy: Int,
             value: String,
             name: String,
             partnerGroupId: Int,
             accountTypeId: Int,
             industryTypeId: Int,
             salesGroupId: Int,
             segmentId: Int,
             description: String,
             isCustomer: Boolean = true,
             isEmployee: Boolean = false,
             isVendor: Boolean = false
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