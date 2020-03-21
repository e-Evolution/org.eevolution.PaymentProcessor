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

import org.eevolution.context.paymentprocessor.domain.model.PartnerBankAccount
import org.eevolution.context.paymentprocessor.infrastructure.database.context._

/**
  * Bank Account Mapping with Database
  */
trait PartnerBankAccountMapping {
  val quotePartnerBankAccount = quote {
    querySchema[PartnerBankAccount]("C_BP_BankAccount",
      _.partnerBankAccountId -> "_BP_BankAccount_ID",
      _.partnerId -> "C_BPartner_ID",
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
      _.accountNo -> "AccountNo",
      _.accountName -> "A_Name",
      _.accountCity -> "A_City",
      _.accountCountry -> "A_Country",
      _.accountState -> "A_State",
      _.accountStreet -> "A_Street",
      _.accountEmail -> "A_EMail",
      _.accountDriverLicense -> "A_Ident_DL",
      _.accountSocialSecurityNo -> "A_Ident_SSN",
      _.accountZip -> "A_Zip",
      _.userId -> "AD_User_ID",
      _.bankAccountType -> "BankAccountType",
      _.partnerBankAcctUse -> "BPBankAcctUse",
      _.creditCardExpMM -> "CreditCardExpMM",
      _.creditCardExpYY -> "CreditCardExpYY",
      _.CreditCardNumber -> "CreditCardNumber",
      _.creditCardType -> "CreditCardType",
      _.creditCardVV -> "CreditCardVV",
      _.isACH -> "IsACH",
      _.isPayrollAccount -> "IsPayrollAccount",
      _.addressVerified -> "R_AvsAddr",
      _.zipVerified -> "R_AvsZip",
      _.routingNo -> "RoutingNo",
      _.iban -> "IBAN",
      _.uuid -> "UUID")
  }
}
