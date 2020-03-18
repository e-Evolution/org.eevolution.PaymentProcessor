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

package org.eevolution.infrastructure.respository

import org.eevolution.domain.model.BankAccount
import org.eevolution.infrastructure.database.context._

/**
  * Bank Account Mapping with Database
  */
trait BankAccountMapping {
  val quoteBankAccount = quote {
    querySchema[BankAccount]("C_BankAccount_ID",
      _.bankId -> "C_Bank_ID",
      _.tenantId -> "AD_Client_ID",
      _.organizationId -> "AD_Org_ID",
      _.isActive -> "IsActive",
      _.created -> "Created",
      _.createdBy -> "CreatedBy",
      _.updated -> "Updated",
      _.updatedBy -> "UpdatedBy",
      _.accountNo -> "AccountNo",
      _.bankAccountType -> "BankAccountType",
      _.bban -> "BBAN",
      _.partnerId -> "C_BPartner_ID",
      _.currencyId -> "C_Currency_ID",
      _.creditLimit -> "CreditLimit",
      _.currentBalance -> "CurrentBalance",
      _.description -> "Description",
      _.iban -> "IBAN",
      _.isDefault -> "isDefault",
      _.isSOTrx -> "IsSOTrx",
      _.paymentExportClass -> "paymentExportClass",
      _.payrollPaymentExportClass -> "PayrollPaymentExportClass",
      _.uuid -> "UUID")
  }
}
