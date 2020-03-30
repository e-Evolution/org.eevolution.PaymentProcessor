package org.eevolution.context.paymentprocessor.infrastructure.respository

import java.sql.Timestamp

import org.compiere.util.Env
import org.eevolution.context.paymentprocessor.api.repository.PaymentRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Payment}
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import org.eevolution.context.paymentprocessor.infrastructure.persistence.model.MPayment
import zio.{Task, ZIO}

import scala.util.Try

final case class PaymentRepositoryLive() extends  PaymentRepository.Service with PaymentMapping {
    override def getById(id: Id):Task[Option[Payment]] =
    for {
          //ctx <- contextService.getContext
          //trx <- contextService.getTransaction
          payment <- ZIO.fromTry {
            Try {
              //val result = prepare(quotePayment.filter(_.paymentId == lift(id)))(trx.getConnection)
              //val payment = new MPayment(ctx, result, trx.getTrxName)
              val payment = run(quotePayment.filter(_.paymentId == lift(id))).headOption
              payment
            }
          }
        } yield payment

    override def save(payment: Payment): Task[Option[Payment]] = for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        payment <- ZIO.fromTry {
          Try {
            val paymentModel = new MPayment(Env.getCtx, payment.paymentId, null)
            /*paymentModel.setA_City(payment.accountCity)
            paymentModel.setA_Country(payment.accountCountry)
            paymentModel.setA_EMail(payment.accountEMail)
            paymentModel.setA_Ident_DL(payment.driverLicense)
            paymentModel.setA_Ident_SSN(payment.socialSecurityNo)
            paymentModel.setA_Name(payment.accountName)
            paymentModel.setA_State(payment.accountState)
            paymentModel.setA_Street(payment.accountStreet)
            paymentModel.setA_Zip(payment.accountZipPostal)
            paymentModel.setAccountNo(payment.accountNo)
            paymentModel.setAD_Org_ID(payment.organizationId)
            paymentModel.setAD_OrgTrx_ID(payment.trxOrganizationId)
            paymentModel.setC_Activity_ID(payment.activityId)
            paymentModel.setC_BankAccount_ID(payment.bankAccountId)
            paymentModel.setC_BP_BankAccount_ID(payment.partnerBankAccountId)
            paymentModel.setC_BPartner_ID(payment.paymentId)
            paymentModel.setC_Campaign_ID(payment.campaignId)
            paymentModel.setC_CashBook_ID(payment.cashBookId)
            paymentModel.setC_Charge_ID(payment.chargeId)
            paymentModel.setC_ConversionType_ID(payment.conversionTypeId)
            paymentModel.setC_Currency_ID(payment.currencyId)
            paymentModel.setC_DocType_ID(payment.documentTypeId)
            paymentModel.setC_Invoice_ID(payment.invoiceId)
            paymentModel.setC_Order_ID(payment.orderId)
            paymentModel.setC_PaymentBatch_ID(payment.paymentBatchId)
            paymentModel.setC_POS_ID(payment.posTerminalId)
            paymentModel.setC_Project_ID(payment.projectId)
            paymentModel.setChargeAmt(payment.chargeAmount.bigDecimal)
            paymentModel.setCheckNo(payment.checkNo)
            paymentModel.setCreditCardExpMM(payment.creditCardExpMM)
            paymentModel.setCreditCardExpYY(payment.creditCardExpYY)
            paymentModel.setCreditCardNumber(payment.creditCardNumber)
            paymentModel.setCreditCardType(payment.creditCardType)
            paymentModel.setCreditCardVV(payment.creditCardVerificationCode)
            paymentModel.setDateAcct(Timestamp.from(payment.accountDate))
            paymentModel.setDateTrx(Timestamp.from(payment.transactionDate))
            paymentModel.setDescription(payment.description)
            paymentModel.setDiscountAmt(payment.discountAmount.bigDecimal)
            paymentModel.setDocAction(payment.documentAction)
            paymentModel.setDocStatus(payment.documentStatus)
            paymentModel.setDocumentNo(payment.documentNo)
            paymentModel.setIsActive(payment.isActive)
            paymentModel.setIsAllocated(payment.isAllocated)
            paymentModel.setIsApproved(payment.isApproved)
            paymentModel.setIsDelayedCapture(payment.isDelayedCapture)
            paymentModel.setIsOnline(payment.isOnlineAccess)
            paymentModel.setIsOverUnderPayment(payment.isOverUnderPayment)
            paymentModel.setIsPrepayment(payment.isPrepayment)
            paymentModel.setIsReceipt(payment.isReceipt)
            paymentModel.setIsReconciled(payment.isReconciled)
            paymentModel.setIsSelfService(payment.isSelfService)
            paymentModel.setIsUnidentifiedPayment(payment.isUnidentifiedPayment)
            paymentModel.setMicr(payment.micr)
            paymentModel.setOProcessing(payment.onlineProcessing)
            paymentModel.setOrig_TrxID(payment.originalTransactionId)
            paymentModel.setOverUnderAmt(payment.overUnderPayment.bigDecimal)
            paymentModel.setPayAmt(payment.paymentAmount.bigDecimal)
            paymentModel.setPONum(payment.poNumber)
            paymentModel.setPosted("Y".equals(payment.posted))
            paymentModel.setProcessed(payment.processed)
            paymentModel.setProcessedOn(payment.processedOn.bigDecimal)
            paymentModel.setProcessing("Y".equals(payment.processing))
            paymentModel.setR_AuthCode(payment.authorizationCode)
            paymentModel.setR_AuthCode_DC(payment.authorizationCodeDC)
            paymentModel.setR_AvsAddr(payment.addressVerified)
            paymentModel.setR_AvsZip(payment.zipVerified)
            paymentModel.setR_CVV2Match(payment.cvvMatch)
            paymentModel.setR_Info(payment.info)
            paymentModel.setR_PnRef(payment.reference)
            paymentModel.setR_PnRef_DC(payment.referenceDC)
            paymentModel.setR_RespMsg(payment.responseMessage)
            paymentModel.setR_Result(payment.result)
            paymentModel.setRef_Payment_ID(payment.referencedPaymentId)
            paymentModel.setReversal_ID(payment.reversalId)
            paymentModel.setRoutingNo(payment.routingNo)
            paymentModel.setSwipe(payment.swipe)
            paymentModel.setTaxAmt(payment.taxAmount.bigDecimal)
            paymentModel.setTenderType(payment.tenderType)
            paymentModel.setTrxType(payment.transactionType)
            paymentModel.setUser1_ID(payment.user1Id)
            paymentModel.setUser2_ID(payment.user2Id)
            paymentModel.setUser3_ID(payment.user3Id)
            paymentModel.setUser4_ID(payment.user4Id)
            paymentModel.setUUID(payment.uuid)
            paymentModel.setVoiceAuthCode(payment.voiceAuthorizationCode)
            paymentModel.setWriteOffAmt(payment.writeOffAmt.bigDecimal)*/
            paymentModel.save()

            //payment.copy(paymentId = paymentModel.get_ID, uuid = paymentModel.getUUID, created = paymentModel.getCreated.toInstant, createdBy = paymentModel.getCreatedBy, updated = paymentModel.getUpdated.toInstant, updatedBy = paymentModel.getUpdatedBy)
            Option(payment)
          }
        }
      } yield payment
}