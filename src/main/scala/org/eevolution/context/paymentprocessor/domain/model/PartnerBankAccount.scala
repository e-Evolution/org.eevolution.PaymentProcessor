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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{DateTime, Id, List, Table, TableDirect, YesNo}

/**
  * Partner Bank Account
  * @param partnerBankAccountId
  * @param partnerId
  * @param bankId
  * @param uuid
  * @param tenantId
  * @param organizationId
  * @param isActive
  * @param created
  * @param createdBy
  * @param updated
  * @param updatedBy
  * @param accountNo
  * @param accountName
  * @param accountCity
  * @param accountCountry
  * @param accountState
  * @param accountStreet
  * @param accountEmail
  * @param accountDriverLicense
  * @param accountSocialSecurityNo
  * @param accountZip
  * @param userId
  * @param bankAccountType
  * @param partnerBankAcctUse
  * @param creditCardExpMM
  * @param creditCardExpYY
  * @param CreditCardNumber
  * @param creditCardType
  * @param creditCardVV
  * @param isACH
  * @param isPayrollAccount
  * @param addressVerified
  * @param zipVerified
  * @param routingNo
  * @param iban
  */
case class PartnerBankAccount(partnerBankAccountId: Id,
                              partnerId: TableDirect,
                              bankId: TableDirect,
                              uuid: String,
                              tenantId: TableDirect,
                              organizationId: TableDirect = 0,
                              isActive: YesNo = true,
                              created: DateTime = Instant.now,
                              createdBy: Table,
                              updated: DateTime = Instant.now,
                              updatedBy: Table,
                              accountNo: String,
                              accountName: String,
                              accountCity: String,
                              accountCountry: String,
                              accountState: String,
                              accountStreet: String,
                              accountEmail: String,
                              accountDriverLicense: String,
                              accountSocialSecurityNo: String,
                              accountZip: String,
                              userId: TableDirect,
                              bankAccountType: List,
                              partnerBankAcctUse: List,
                              creditCardExpMM: Int,
                              creditCardExpYY: Int,
                              CreditCardNumber: String,
                              creditCardType: String,
                              creditCardVV: String,
                              isACH: YesNo,
                              isPayrollAccount: YesNo,
                              addressVerified: List,
                              zipVerified: List,
                              routingNo: List,
                              iban: String
                             )
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_BP_BankAccount"
  override val identifier: String = "C_BP_BankAccount_ID"
}

object PartnerBankAccount {

  def create(partnerBankAccountId: Id,
             partnerId: TableDirect,
             bankId: TableDirect,
             uuid: String,
             tenantId: TableDirect,
             organizationId: TableDirect = 0,
             isActive: YesNo = true,
             created: DateTime = Instant.now,
             createdBy: Table,
             updated: DateTime = Instant.now,
             updatedBy: Table,
             accountNo: String,
             accountName: String,
             accountCity: String,
             accountCountry: String,
             accountState: String,
             accountStreet: String,
             accountEmail: String,
             accountDriverLicense: String,
             accountSocialSecurityNo: String,
             accountZip: String,
             userId: TableDirect,
             bankAccountType: List,
             partnerBankAcctUse: List,
             creditCardExpMM: Int,
             creditCardExpYY: Int,
             CreditCardNumber: String,
             creditCardType: String,
             creditCardVV: String,
             isACH: YesNo,
             isPayrollAccount: YesNo,
             addressVerified: List,
             zipVerified: List,
             routingNo: List,
             iban: String
            ): PartnerBankAccount =
    PartnerBankAccount(partnerBankAccountId,
    partnerId,
    bankId,
    uuid,
    tenantId,
    organizationId,
    isActive,
    created,
    createdBy,
    updated,
    updatedBy,
    accountNo,
    accountName,
    accountCity,
    accountCountry,
    accountState,
    accountStreet,
    accountEmail,
    accountDriverLicense,
    accountSocialSecurityNo,
    accountZip,
    userId,
    bankAccountType,
    partnerBankAcctUse,
    creditCardExpMM,
    creditCardExpYY,
    CreditCardNumber,
    creditCardType,
    creditCardVV,
    isACH,
    isPayrollAccount,
    addressVerified,
    zipVerified,
    routingNo,
    iban
  )
}