package org.eevolution.context.paymentprocessor.api.repository

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.DocumentType
import org.eevolution.context.paymentprocessor.infrastructure.respository.DocumentTypeRepositoryLive
import zio.{Has, Task, ZLayer}

/**
  * Standard Implementation for Document Type Repository
  */
object DocumentTypeRepository {

  type DocumentTypeRepository =  Has[DocumentTypeRepository.Service]
  trait Service {
    def getById(id: Int): Task[Option[DocumentType]]
  }
  def live : ZLayer[Any, Throwable, Has[Service]] =   ZLayer.succeed(DocumentTypeRepositoryLive ()) //ZLayer.fromService[Context.Service , Service] { (contextService) => DocumentTypeRepositoryLive (contextService)}

}
