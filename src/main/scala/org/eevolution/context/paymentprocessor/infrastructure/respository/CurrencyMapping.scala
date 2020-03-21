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

import org.eevolution.context.paymentprocessor.domain.model.Currency
import org.eevolution.context.paymentprocessor.infrastructure.database.context._

/**
  * Bank Mapping with Database
  */
trait CurrencyMapping {
  def quoteCurrency = quote(
    querySchema[Currency]("C_Currency",
      _.currencyId -> "C_Currency_ID",
      _.tenantId -> "AD_Client_ID",
      _.organizationId -> "AD_Org_ID",
      _.isActive -> "IsActive",
      _.created -> "Created",
      _.createdBy -> "CreatedBy",
      _.updated -> "Updated",
      _.updatedBy -> "UpdatedBy",
      _.value -> "Value",
      _.name -> "Name",
      _.description -> "Description",
      _.costingPrecision -> "CostingPrecision",
      _.curSymbol -> "CurSymbol",
      _.emuEntryDate -> "EMUEntryDate",
      _.emuRate -> "EMURate",
      _.isEMUMember -> "IsEMUMember",
      _.isEuro -> "IsEuro",
      _.isoCode -> "ISO_Code",
      _.roundOffFactor -> "RoundOffFactor",
      _.stdPrecision -> "StdPrecision",
      _.uuid -> "UUID")
  )
}
