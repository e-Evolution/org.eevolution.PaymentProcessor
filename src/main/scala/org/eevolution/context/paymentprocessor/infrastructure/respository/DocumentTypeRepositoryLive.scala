package org.eevolution.context.paymentprocessor.infrastructure.respository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.repository.DocumentTypeRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.DocumentType
import org.eevolution.context.paymentprocessor.infrastructure.database.context._
import zio.Task

import scala.util.Try

case class DocumentTypeRepositoryLive() extends DocumentTypeRepository.Service with DocumentTypeMapping {
  override def getById(id: Int): Task[Option[DocumentType]] = for {
        //ctx <- contextService.getContext
        //trx <- contextService.getTransaction
        documentType <- Task.fromTry {
          Try {
            //val result = prepare(quoteBankAccount.filter(_.bankAccountId == lift(id)))(trx.getConnection).executeQuery
            //val bank = new MBankAccount(ctx, result, trx.getTrxName)
            val documentType = run(quoteDocumentType.filter(_.documentTypeId == lift(id))).headOption
            documentType
          }
        }
      } yield documentType
}
