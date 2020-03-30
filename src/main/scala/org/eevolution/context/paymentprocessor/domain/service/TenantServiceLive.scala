package org.eevolution.context.paymentprocessor.domain.service


import org.eevolution.context.paymentprocessor.api.repository.TenantRepository
import org.eevolution.context.paymentprocessor.api.service.TenantService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Tenant}
import zio.Task


final case class TenantServiceLive(tenantRepository : TenantRepository.Service) extends TenantService.Service {
  override def getById(id: Id): Task[Option[Tenant]] = tenantRepository.getById(id)
}
