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


import org.eevolution.context.paymentprocessor.domain.model.Payment
import org.eevolution.context.paymentprocessor.infrastructure.database.context._

/**
  * Payment Mapping with Database
  */
trait PaymentMapping {
  def quotePayment = quote {
    querySchema[Payment]("C_Payment",
      _.paymentId -> "C_Payment_ID",
      _.uuid -> "UUID",
      _.tenantId -> "AD_Client_ID",
      _.organizationId -> "AD_Org_ID",
      _.isActive -> "IsActive",
      _.created -> "Created",
      _.createdBy -> "CreatedBy",
      _.updated -> "Updated",
      _.updatedBy -> "UpdatedBy",
      _.accountNo -> "AccountNo",
      _.accountCity -> "A_City",
      _.accountCountry -> "A_Country",
      _.trxOrganizationId -> "AD_OrgTrx_ID",
      _.accountEMail -> "A_EMail",
      _.driverLicense -> "A_Ident_DL",
      _.socialSecurityNo -> "A_Ident_SSN",
      _.accountName -> "A_Name",
      _.accountState -> "A_State",
      _.accountStreet -> "A_Street",
      _.accountZipPostal -> "A_Zip",
      _.activityId -> "C_Activity_ID",
      _.bankAccountId -> "C_BankAccount_ID",
      _.partnerId -> "C_BPartner_ID",
      _.partnerBankAccountId -> "C_BP_BankAccount_ID",
      _.campaignId -> "C_Campaign_ID",
      _.cashBookId -> "C_CashBook_ID",
      _.chargeId -> "C_Charge_ID",
      _.conversionTypeId -> "C_ConversionType_ID",
      _.currencyId -> "C_Currency_ID",
      _.documentTypeId -> "C_DocType_ID",
      _.chargeAmount -> "ChargeAmt",
      _.checkNo -> "CheckNo",
      _.invoiceId -> "C_Invoice_ID",
      _.orderId -> "C_Order_ID",
      _.paymentBatchId -> "C_PaymentBatch_ID",
      _.posTerminalId -> "C_POS_ID",
      _.projectId -> "C_Project_ID",
      _.creditCardExpMM -> "CreditCardExpMM",
      _.creditCardExpYY -> "CreditCardExpYY",
      _.creditCardNumber -> "CreditCardNumber",
      _.creditCardType -> "CreditCardType",
      _.creditCardVerificationCode -> "CreditCardVV",
      _.accountDate -> "DateAcct",
      _.transactionDate -> "DateTrx",
      _.description -> "Description",
      _.discountAmount -> "DiscountAmt",
      _.documentAction -> "DocAction",
      _.documentStatus -> "DocStatus",
      _.documentNo -> "DocumentNo",
      _.isAllocated -> "IsAllocated",
      _.isApproved -> "IsApproved",
      _.isDelayedCapture -> "IsDelayedCapture",
      _.isOnlineAccess -> "IsOnline",
      _.isOverUnderPayment -> "IsOverUnderPayment",
      _.isPrepayment -> "IsPrepayment",
      _.isReceipt -> "IsReceipt",
      _.isReconciled -> "IsReconciled",
      _.isSelfService -> "IsSelfService",
      _.isUnidentifiedPayment -> "IsUnidentifiedPayment",
      _.micr -> "Micr",
      _.onlineProcessing -> "OProcessing",
      _.originalTransactionId -> "Orig_TrxID",
      _.overUnderPayment -> "OverUnderAmt",
      _.paymentAmount -> "PayAmt",
      _.poNumber -> "PONum",
      _.posted -> "Posted",
      _.processed -> "Processed",
      _.processedOn -> "ProcessedOn",
      _.processing -> "Processing",
      _.authorizationCode -> "R_AuthCode",
      _.authorizationCodeDC -> "R_AuthCode_DC",
      _.addressVerified -> "R_AvsAddr",
      _.zipVerified -> "R_AvsZip",
      _.cvvMatch -> "R_CVV2Match",
      _.referencedPaymentId -> "Ref_Payment_ID",
      _.paymentRelatedId -> "RelatedPayment_ID",
      _.reversalId -> "Reversal_ID",
      _.info -> "R_Info",
      _.routingNo -> "RoutingNo",
      _.reference -> "R_PnRef",
      _.referenceDC -> "R_PnRef_DC",
      _.responseMessage -> "R_RespMsg",
      _.result -> "R_Result",
      _.serviceContractId -> "S_Contract_ID",
      _.swipe -> "Swipe",
      _.taxAmount -> "TaxAmt",
      _.tenderType -> "TenderType",
      _.transactionType -> "TrxType",
      _.user1Id -> "User1_ID",
      _.user2Id -> "User2_ID",
      _.user3Id -> "User3_ID",
      _.user4Id -> "User4_ID",
      _.voiceAuthorizationCode -> "VoiceAuthCode",
      _.writeOffAmt -> "WriteOffAmt"
    )
  }
}
