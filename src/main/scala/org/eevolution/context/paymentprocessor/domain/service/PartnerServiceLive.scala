package org.eevolution.context.paymentprocessor.domain.service

import org.eevolution.context.paymentprocessor.api.Context
import org.eevolution.context.paymentprocessor.api.Context.Context
import org.eevolution.context.paymentprocessor.api.repository.PartnerRepository
import org.eevolution.context.paymentprocessor.api.service.PartnerService
import org.eevolution.context.paymentprocessor.domain.ubiquitouslanguage.{Id, Partner}
import zio.RIO

case class PartnerServiceLive( partnerRepository: PartnerRepository.Service) extends PartnerService.Service{

  override def getById(partnerId: Id): RIO[Any, Option[Partner]] = partnerRepository.getById(partnerId)
}
