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

/**
  * Payment Processor Entity
  *
  * @param paymentProcessorId
  * @param uuid
  * @param tenantId
  * @param organizationId
  * @param isActive
  * @param created
  * @param createdBy
  * @param updated
  * @param updatedBy
  * @param acceptAMEX
  * @param acceptATM
  * @param acceptCheck
  * @param acceptCorporate
  * @param acceptDiners
  * @param acceptDirectDebit
  * @param acceptDirectDeposit
  * @param acceptDiscover
  * @param acceptMC
  * @param acceptVisa
  * @param sequenceId
  * @param bankAccountId
  * @param currencyId
  * @param commission
  * @param costPerTrx
  * @param hostAddress
  * @param hostPort
  * @param minimumAmt
  * @param partnerId
  * @param password
  * @param payProcessorClass
  * @param proxyAddress
  * @param proxyPassword
  * @param proxyPort
  * @param requireVV
  * @param userId
  * @param vendorId
  * @param value
  * @param name
  * @param description
  */
case class PaymentProcessor(paymentProcessorId: Int,
                            uuid: String,
                            tenantId: Int,
                            organizationId: Int = 0,
                            isActive: Boolean = true,
                            created: Instant = Instant.now,
                            createdBy: Int,
                            updated: Instant = Instant.now,
                            updatedBy: Int,
                            acceptAMEX: Boolean = false,
                            acceptATM: Boolean = false,
                            acceptCheck: Boolean = false,
                            acceptCorporate: Boolean = false,
                            acceptDiners: Boolean = false,
                            acceptDirectDebit: Boolean = false,
                            acceptDirectDeposit: Boolean = false,
                            acceptDiscover: Boolean = false,
                            acceptMC: Boolean = false,
                            acceptVisa: Boolean = false,
                            sequenceId: Int,
                            bankAccountId: Int,
                            currencyId: Int,
                            commission: BigDecimal,
                            costPerTrx: BigDecimal,
                            hostAddress: String,
                            hostPort: Int,
                            minimumAmt: String,
                            partnerId: String,
                            password: String,
                            payProcessorClass: String,
                            proxyAddress: String,
                            proxyPassword: String,
                            proxyPort: String,
                            requireVV: String,
                            userId: String,
                            vendorId: String,
                            name: String,
                            description: String
                           )
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_PaymentProcessor"
  override val identifier: String = "C_PaymentProcessorC_PaymentProcessor"
}

object PaymentProcessor {

  def create(paymentProcessorId: Int,
             uuid: String,
             tenantId: Int,
             organizationId: Int = 0,
             isActive: Boolean = true,
             created: Instant = Instant.now,
             createdBy: Int,
             updated: Instant = Instant.now,
             updatedBy: Int,
             acceptAMEX: Boolean = false,
             acceptATM: Boolean = false,
             acceptCheck: Boolean = false,
             acceptCorporate: Boolean = false,
             acceptDiners: Boolean = false,
             acceptDirectDebit: Boolean = false,
             acceptDirectDeposit: Boolean = false,
             acceptDiscover: Boolean = false,
             acceptMC: Boolean = false,
             acceptVisa: Boolean = false,
             sequenceId: Int,
             bankAccountId: Int,
             currencyId: Int,
             commission: BigDecimal,
             costPerTrx: BigDecimal,
             hostAddress: String,
             hostPort: Int,
             minimumAmt: String,
             partnerId: String,
             password: String,
             payProcessorClass: String,
             proxyAddress: String,
             proxyPassword: String,
             proxyPort: String,
             requireVV: String,
             userId: String,
             vendorId: String,
             name: String,
             description: String
            ): PaymentProcessor =
    PaymentProcessor(
      paymentProcessorId,
      uuid,
      tenantId,
      organizationId,
      isActive,
      created,
      createdBy,
      updated,
      updatedBy,
      acceptAMEX,
      acceptATM,
      acceptCheck,
      acceptCorporate,
      acceptDiners,
      acceptDirectDebit,
      acceptDirectDeposit,
      acceptDiscover,
      acceptMC,
      acceptVisa,
      sequenceId,
      bankAccountId,
      currencyId,
      commission,
      costPerTrx,
      hostAddress,
      hostPort,
      minimumAmt,
      partnerId,
      password,
      payProcessorClass,
      proxyAddress,
      proxyPassword,
      proxyPort,
      requireVV,
      userId,
      vendorId,
      name,
      description
    )
}
