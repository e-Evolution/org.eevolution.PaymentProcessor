package org.eevolution.context.paymentprocessor.api.process

import zio.ZIO

trait PaymentOnlineService {
  def paymentOnLineService : PaymentOnlineService.Service
}

object PaymentOnlineService {

  type PaymentOnlineServiceEnvironment = PaymentOnlineService
  trait Service {
    def prepare() : ZIO[PaymentOnlineServiceEnvironment, Throwable, Unit]
    def doIt() : ZIO[PaymentOnlineServiceEnvironment, Throwable , String]
  }

  trait Live extends PaymentOnlineService

  object Live extends Live {
    override def paymentOnLineService: Service = new Service {
      override def prepare(): ZIO[PaymentOnlineServiceEnvironment, Throwable, Unit] = ???

      override def doIt(): ZIO[PaymentOnlineServiceEnvironment, Throwable, String] = ???
    }
  }

}