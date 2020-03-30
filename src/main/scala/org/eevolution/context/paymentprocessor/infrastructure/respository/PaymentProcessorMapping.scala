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

import org.eevolution.context.paymentprocessor.domain.model.PaymentProcessor
import org.eevolution.context.paymentprocessor.infrastructure.database.context._

/**
  * Payment Processor Mapping with Database
  */
trait PaymentProcessorMapping {
  val quotePaymentProcessor = quote {
    querySchema[PaymentProcessor]("C_PaymentProcessor",
      _.paymentProcessorId -> "C_PaymentProcessor_ID",
      _.tenantId -> "AD_Client_ID",
      _.organizationId -> "AD_Org_ID",
      _.isActive -> "IsActive",
      _.created -> "Created",
      _.createdBy -> "CreatedBy",
      _.updated -> "Updated",
      _.updatedBy -> "UpdatedBy",
      _.acceptAMEX -> "AcceptAMEX",
      _.acceptATM -> "AcceptATM",
      _.acceptCheck -> "AcceptCheck",
      _.acceptCorporate -> "AcceptCorporate",
      _.acceptDiners -> "AcceptDiners",
      _.acceptDirectDebit -> "AcceptDirectDebit",
      _.acceptDirectDeposit -> "AcceptDirectDeposit",
      _.acceptDiscover -> "AcceptDiscover",
      _.acceptMC -> "AcceptMC",
      _.acceptVisa -> "AcceptVisa",
      _.sequenceId -> "AD_Sequence_ID",
      _.bankAccountId -> "C_BankAccount_ID",
      _.currencyId -> "C_Currency_ID",
      _.commission -> "Commission",
      _.costPerTrx -> "CostPerTrx",
      _.hostAddress -> "HostAddress",
      _.hostPort -> "HostPort",
      _.minimumAmt -> "MinimumAmt",
      _.partnerId -> "PartnerID",
      _.password -> "Password",
      _.payProcessorClass -> "PayProcessorClass",
      _.proxyAddress -> "ProxyAddress",
      _.proxyPassword -> "ProxyPassword",
      _.proxyPort -> "ProxyPort",
      _.requireVV -> "RequireVV",
      _.userId -> "UserID",
      _.vendorId -> "VendorID",
      _.name -> "Name",
      _.description -> "Description",
      _.uuid -> "UUID")
  }
}
