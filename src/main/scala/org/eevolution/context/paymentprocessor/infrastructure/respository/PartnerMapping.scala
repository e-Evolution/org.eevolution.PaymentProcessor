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

package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.domain.model.Partner
import org.eevolution.context.paymentprocessor.infrastructure.database.context._

/**
  * Partner Mapping with Database
  */
trait PartnerMapping {
  def quotePartner = quote(
    querySchema[Partner]("C_BPartner",
      _.partnerId -> "C_BPartner_ID",
      _.tenantId -> "AD_Client_ID",
      _.organizationId -> "AD_Org_ID",
      _.isActive -> "IsActive",
      _.created -> "Created",
      _.createdBy -> "CreatedBy",
      _.updated -> "Updated",
      _.updatedBy -> "UpdatedBy",
      _.value -> "Value",
      _.name -> "Name",
      _.partnerGroupId -> "C_BPartner_ID",
      _.accountTypeId -> "C_BP_AccountType_ID",
      _.industryTypeId -> "C_BP_IndustryType_ID",
      _.salesGroupId -> "C_BP_Group_ID",
      _.segmentId -> "C_BP_Segment_ID",
      _.isCustomer -> "IsCustomer",
      //-.isEmployee -> "IsEmployee",
      //-.isVendor -> "IsVendor",
      _.uuid -> "UUID")
  )
}
