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
  * Bank Account Entity
  *
  * @param bankAccountId
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
  * @param bankAccountType
  * @param bban
  * @param partnerId
  * @param currencyId
  * @param creditLimit
  * @param currentBalance
  * @param description
  * @param iban
  * @param isDefault
  * @param isSOTrx
  * @param paymentExportClass
  * @param payrollPaymentExportClass
  */
case class BankAccount(bankAccountId: Int,
                       bankId: Int,
                       uuid: String,
                       tenantId: Int,
                       organizationId: Int = 0,
                       isActive: Boolean = true,
                       created: Instant = Instant.now,
                       createdBy: Int,
                       updated: Instant = Instant.now,
                       updatedBy: Int,
                       accountNo: String,
                       bankAccountType: String,
                       bban: String,
                       partnerId: Int,
                       currencyId: Int,
                       creditLimit: BigDecimal,
                       currentBalance: BigDecimal,
                       description: String,
                       iban: String,
                       isDefault: Boolean = true,
                       isSOTrx: Boolean = true,
                       paymentExportClass: String,
                       payrollPaymentExportClass: String,
                      )
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_BankAccount"
  override val identifier: String = "C_BankAccount_ID"
}

object BankAccount {

  def create(bankAccountId: Int,
             bankId: Int,
             uuid: String,
             tenantId: Int,
             organizationId: Int = 0,
             isActive: Boolean = true,
             created: Instant = Instant.now,
             createdBy: Int,
             updated: Instant = Instant.now,
             updatedBy: Int,
             accountNo: String,
             bankAccountType: String,
             bban: String,
             partnerId: Int,
             currencyId: Int,
             creditLimit: BigDecimal,
             currentBalance: BigDecimal,
             description: String,
             iban: String,
             isDefault: Boolean = true,
             isSOTrx: Boolean = true,
             paymentExportClass: String,
             payrollPaymentExportClass: String
            ): BankAccount =
    BankAccount(
      bankAccountId,
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
      bankAccountType,
      bban,
      partnerId,
      currencyId,
      creditLimit,
      currentBalance: BigDecimal,
      description,
      iban,
      isSOTrx,
      isDefault,
      paymentExportClass,
      payrollPaymentExportClass
    )
}