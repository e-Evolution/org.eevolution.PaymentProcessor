package org.eevolution.context.paymentprocessor.domain.service

import java.security.SecureRandom

import org.compiere.util.{Secure, SecureEngine}
import org.eevolution.context.paymentprocessor.api.service.SecurityService
import zio.{RIO, ZIO}

import scala.util.Try

case class SecurityServiceLive() extends SecurityService.Service {
  override def setHash(password: String): RIO[Any, Option[String]] =
    ZIO.fromTry(
      Try {
        val random = SecureRandom.getInstance("SHA1PRNG")
        val salt = new Array[Byte](8)
        random.nextBytes(salt)
        val hash = SecureEngine.getSHA512Hash(1000, password, salt)
        val maybeHash = Option(Secure.convertToHexString(salt))
        maybeHash
      }
    )

  override def getHash(password: String, salt: String): RIO[Any, Option[String]] = ZIO.fromTry(
    Try {
      SecureEngine.getSHA512Hash(1000, password, Secure.convertHexString(salt))
      val passwordHash = Option(SecureEngine.getSHA512Hash(1000, password, Secure.convertHexString(salt)))
      passwordHash
    }
  )
}
