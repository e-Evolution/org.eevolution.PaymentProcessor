package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.DocumentTypeRepository
import org.eevolution.context.paymentprocessor.api.service.DocumentTypeService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{DocumentType, Id}
import zio.RIO

case class DocumentTypeServiceLive(documentTypeRepository : DocumentTypeRepository.Service) extends DocumentTypeService.Service {
  override def getById(id: Id): RIO[Any, Option[DocumentType]] = documentTypeRepository.getById(id)
}
