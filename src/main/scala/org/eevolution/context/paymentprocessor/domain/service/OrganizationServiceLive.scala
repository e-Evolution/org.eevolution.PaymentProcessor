package org.eevolution.context.paymentprocessor.domain.service


import org.eevolution.context.paymentprocessor.api.service.OrganizationService
import org.eevolution.context.paymentprocessor.api.repository.OrganizationRepository
import org.eevolution.context.paymentprocessor.api.repository.OrganizationRepository.OrganizationRepository
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Organization}
import zio.RIO

final case class OrganizationServiceLive(organizationRepository : OrganizationRepository.Service) extends OrganizationService.Service {
  override def getById(id: Id): RIO[OrganizationRepository, Option[Organization]] = organizationRepository.getById(id)
}