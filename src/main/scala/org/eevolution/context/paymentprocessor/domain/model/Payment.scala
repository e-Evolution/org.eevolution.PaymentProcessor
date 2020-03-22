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

import org.eevolution.context.paymentprocessor.UbiquitousLanguage.{Amount, Button, Date, DateTime, Id, List, Number, Search, Table, TableDirect, YesNo}
import org.eevolution.context.paymentprocessor.domain.valueobject.{DocumentStatus, PaymentCreditCardType, PaymentTenderType, PaymentTrxType}

/**
  * Payment Entity
  *
  * @param paymentId
  * @param uuid
  * @param tenantId
  * @param organizationId
  * @param isActive
  * @param created
  * @param createdBy
  * @param updated
  * @param updatedBy
  * @param accountNo
  * @param accountCity
  * @param accountCountry
  * @param trxOrganizationId
  * @param accountEMail
  * @param driverLicense
  * @param socialSecurityNo
  * @param accountName
  * @param accountState
  * @param accountStreet
  * @param accountZipPostal
  * @param activityId
  * @param bankAccountId
  * @param partnerId
  * @param partnerBankAccountId
  * @param campaignId
  * @param cashBookId
  * @param chargeId
  * @param currencyTypeId
  * @param currencyId
  * @param documentTypeId
  * @param chargeAmount
  * @param checkNo
  * @param invoiceId
  * @param orderId
  * @param paymentBatchId
  * @param posTerminalId
  * @param projectId
  * @param creditCardType
  * @param creditCardExpMM
  * @param creditCardExpYY
  * @param creditCardNumber
  * @param creditCardVerificationCode
  * @param accountDate
  * @param transactionDate
  * @param description
  * @param discountAmount
  * @param documentAction
  * @param documentStatus
  * @param documentNo
  * @param isAllocated
  * @param isApproved
  * @param isDelayedCapture
  * @param isOnlineAccess
  * @param isOverUnderPayment
  * @param isPrepayment
  * @param isReceipt
  * @param isReconciled
  * @param isSelfService
  * @param isUnidentifiedPayment
  * @param micr
  * @param onlineProcessing
  * @param originalTransactionId
  * @param overUnderPayment
  * @param paymentAmount
  * @param poNumber
  * @param posted
  * @param processed
  * @param processedOn
  * @param processing
  * @param authorizationCode
  * @param authorizationCodeDC
  * @param addressVerified
  * @param zipVerified
  * @param cvvMatch
  * @param referencedPaymentId
  * @param paymentRelatedId
  * @param reversalId
  * @param info
  * @param routingNo
  * @param reference
  * @param referenceDC
  * @param responseMessage
  * @param result
  * @param serviceContractId
  * @param swipe
  * @param taxAmount
  * @param tenderType
  * @param transactionType
  * @param user1Id
  * @param user2Id
  * @param user3Id
  * @param user4Id
  * @param voiceAuthorizationCode
  * @param writeOffAmt
  */
case class Payment(paymentId: Id,
                   uuid: String,
                   tenantId: TableDirect,
                   organizationId: TableDirect = 0,
                   isActive: YesNo = true,
                   created: DateTime = Instant.now,
                   createdBy: Table,
                   updated: DateTime = Instant.now,
                   updatedBy: Table,
                   accountNo: String,
                   accountCity: String,
                   accountCountry: String,
                   trxOrganizationId: Table,
                   accountEMail: String,
                   driverLicense: String,
                   socialSecurityNo: String,
                   accountName: String,
                   accountState: String,
                   accountStreet: String,
                   accountZipPostal: String,
                   activityId: TableDirect,
                   bankAccountId: TableDirect,
                   partnerId: Search,
                   partnerBankAccountId: TableDirect,
                   campaignId: TableDirect,
                   cashBookId: TableDirect,
                   chargeId: TableDirect,
                   currencyTypeId: TableDirect,
                   currencyId: TableDirect,
                   documentTypeId: TableDirect,
                   chargeAmount: Amount,
                   checkNo: String,
                   invoiceId: Search,
                   orderId: Search,
                   paymentBatchId: TableDirect,
                   posTerminalId: TableDirect,
                   projectId: TableDirect,
                   creditCardType: List = PaymentCreditCardType.MasterCard.value,
                   creditCardExpMM: Int,
                   creditCardExpYY: Int,
                   creditCardNumber: String,
                   creditCardVerificationCode: String,
                   accountDate: Date,
                   transactionDate: Date,
                   description: String,
                   discountAmount: Amount,
                   documentAction: Button,
                   documentStatus: List = DocumentStatus.Drafted.value,
                   documentNo: String,
                   isAllocated: YesNo = false,
                   isApproved: YesNo = false,
                   isDelayedCapture: YesNo = false,
                   isOnlineAccess: YesNo = false,
                   isOverUnderPayment: YesNo = false,
                   isPrepayment: YesNo = false,
                   isReceipt: YesNo = false,
                   isReconciled: YesNo = false,
                   isSelfService: YesNo = false,
                   isUnidentifiedPayment: YesNo = false,
                   micr: String,
                   onlineProcessing: Button,
                   originalTransactionId: String,
                   overUnderPayment: Amount,
                   paymentAmount: Amount,
                   poNumber: String,
                   posted: Button,
                   processed: YesNo = false,
                   processedOn: Number,
                   processing: Button,
                   authorizationCode: String,
                   authorizationCodeDC: String,
                   addressVerified: List,
                   zipVerified: List,
                   cvvMatch: YesNo = false,
                   referencedPaymentId: Table,
                   paymentRelatedId: Table,
                   reversalId: Table,
                   info: String,
                   routingNo: String,
                   reference: String,
                   referenceDC: String,
                   responseMessage: String,
                   result: String,
                   serviceContractId: TableDirect,
                   swipe: String,
                   taxAmount: Amount,
                   tenderType: List = PaymentTenderType.Check.value,
                   transactionType: List = PaymentTrxType.Sales.value,
                   user1Id: Table,
                   user2Id: Table,
                   user3Id: Table,
                   user4Id: Table,
                   voiceAuthorizationCode: String,
                   writeOffAmt: Amount)
  extends DomainModel with ActiveEnabled
    with Identifiable
    with Traceable {
  override type ActiveEnabled = this.type
  override type Identifiable = this.type
  override type Traceable = this.type

  override def Id: Int = tenantId

  override val entityName: String = "C_Bank"
  override val identifier: String = "C_Bank_ID"
}

object Payment {

  val EntityName = "C_Bank_ID"
  val BankId = "C_Bank_ID"
  val TenantId = "AD_Client_ID"
  val OrganizationId = "AD_Org_ID"
  val IsActive = "IsActive"
  val Created = "Created"
  val CreatedBy = "CreatedBy"
  val Updated = "Updated"
  val UpdatedBy = "UpdatedBy"
  val EntityId = "Ad_Table_ID"
  val AcceptAMEX = "AcceptAMEX"
  val AcceptATM = "AcceptATM"
  val AcceptCheck = "AcceptCheck"
  val AcceptCorporate = "AcceptATM"
  val AcceptDiners = "AcceptDiners"
  val AcceptDirectDebit = "AcceptDirectDebit"
  val AcceptDirectDeposit = "AcceptDirectDeposit"
  val AcceptDiscover = "AcceptDiscover"
  val AcceptMC = "AcceptMC"
  val AcceptVisa = "AcceptVisa"
  val SequenceId = "AD_Sequence_ID"
  val BankAccountId = "C_BankAccount_ID"
  val CurrencyId = "C_Currency_ID"
  val Commission = "Commission"
  val CostPerTrx = "CostPerTrx"
  val HostAddress = "HostAddress"
  val HostPort = "HostPort"
  val MinimumAmt = "MinimumAmt"
  val PartnerId = "C_Partner_ID"
  val Password = "Password"
  val PayProcessorClass = "PayProcessorClass"
  val ProxyAddress = "ProxyAddress"
  val ProxyPassword = "ProxyPassword"
  val ProxyPort = "ProxyPort"
  val RequireVV = "RequireVV"
  val UserId = "UserID"
  val VendorId = "VendorID"
  val Value = "Value"
  val Name = "Name"
  val UUID = "UUID"


  def create(paymentId: Id,
             uuid: String,
             tenantId: TableDirect,
             organizationId: TableDirect = 0,
             isActive: YesNo = true,
             created: DateTime = Instant.now,
             createdBy: Table,
             updated: DateTime = Instant.now,
             updatedBy: Table,
             accountNo: String,
             accountCity: String,
             accountCountry: String,
             trxOrganizationId: Table,
             accountEMail: String,
             driverLicense: String,
             socialSecurityNo: String,
             accountName: String,
             accountState: String,
             accountStreet: String,
             accountZipPostal: String,
             activityId: TableDirect,
             bankAccountId: TableDirect,
             partnerId: Search,
             partnerBankAccountId: TableDirect,
             campaignId: TableDirect,
             cashBookId: TableDirect,
             chargeId: TableDirect,
             currencyTypeId: TableDirect,
             currencyId: TableDirect,
             documentTypeId: TableDirect,
             chargeAmount: Amount,
             checkNo: String,
             invoiceId: Search,
             orderId: Search,
             paymentBatchId: TableDirect,
             posTerminalId: TableDirect,
             projectId: TableDirect,
             creditCardType: List,
             creditCardExpMM: Int,
             creditCardExpYY: Int,
             creditCardNumber: String,
             creditCardVerificationCode: String,
             accountDate: Date,
             transactionDate: Date,
             description: String,
             discountAmount: Amount,
             documentAction: Button,
             documentStatus: List,
             documentNo: String,
             isAllocated: YesNo = false,
             isApproved: YesNo = false,
             isDelayedCapture: YesNo = false,
             isOnlineAccess: YesNo = false,
             isOverUnderPayment: YesNo = true,
             isPrepayment: YesNo = false,
             isReceipt: YesNo = false,
             isReconciled: YesNo = false,
             isSelfService: YesNo = false,
             isUnidentifiedPayment: YesNo = false,
             micr: String,
             onlineProcessing: Button,
             originalTransactionId: String,
             overUnderPayment: Amount,
             paymentAmount: Amount,
             poNumber: String,
             posted: Button,
             processed: YesNo = false,
             processedOn: Number,
             processing: Button,
             authorizationCode: String,
             authorizationCodeDC: String,
             addressVerified: List,
             zipVerified: List,
             cvvMatch: YesNo = false,
             referencedPaymentId: Table,
             paymentRelatedId: Table,
             reversalId: Table,
             info: String,
             routingNo: String,
             reference: String,
             referenceDC: String,
             responseMessage: String,
             result: String,
             serviceContractId: TableDirect,
             swipe: String,
             taxAmount: Amount,
             tenderType: List,
             transactionType: List = PaymentTrxType.Sales.value,
             user1Id: Table,
             user2Id: Table,
             user3Id: Table,
             user4Id: Table,
             voiceAuthorizationCode: String,
             writeOffAmt: Amount
            ): Payment = Payment(
    0,
    uuid,
    tenantId,
    organizationId,
    isActive,
    created,
    createdBy,
    updated,
    updatedBy,
    accountNo,
    accountCity,
    accountCountry,
    trxOrganizationId,
    accountEMail,
    driverLicense,
    socialSecurityNo,
    accountName,
    accountState,
    accountStreet,
    accountZipPostal,
    activityId,
    bankAccountId,
    partnerId,
    partnerBankAccountId,
    campaignId,
    cashBookId,
    chargeId,
    currencyTypeId,
    currencyId,
    documentTypeId,
    chargeAmount,
    checkNo,
    invoiceId,
    orderId,
    paymentBatchId,
    posTerminalId,
    projectId,
    creditCardType,
    creditCardExpMM,
    creditCardExpYY,
    creditCardNumber,
    creditCardVerificationCode,
    accountDate,
    transactionDate,
    description,
    discountAmount,
    documentAction,
    documentStatus,
    documentNo,
    isAllocated,
    isApproved,
    isDelayedCapture,
    isOnlineAccess,
    isOverUnderPayment,
    isPrepayment,
    isReceipt,
    isReconciled,
    isSelfService,
    isUnidentifiedPayment,
    micr,
    onlineProcessing,
    originalTransactionId,
    overUnderPayment,
    paymentAmount,
    poNumber,
    posted,
    processed,
    processedOn,
    processing,
    authorizationCode,
    authorizationCodeDC,
    addressVerified,
    zipVerified,
    cvvMatch,
    referencedPaymentId,
    paymentRelatedId,
    reversalId,
    info,
    routingNo,
    reference,
    referenceDC,
    responseMessage,
    result,
    serviceContractId,
    swipe,
    taxAmount,
    tenderType,
    transactionType,
    user1Id,
    user2Id,
    user3Id,
    user4Id,
    voiceAuthorizationCode,
    writeOffAmt
  )
}
