package tokens

import java.util.UUID

import com.nongped.microservices.auth.{Token, TokenStr}
import javax.inject.{Inject, Singleton}
import utils.Contexts

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TokenService @Inject()(contexts: Contexts, tokensDao: TokenDao) {
  def createToken(key: String)(implicit exec: ExecutionContext): Future[Token] = {
    tokensDao.getTokenFromKey(key).flatMap {
      case Some(token) =>
        if (token.validTill <= System.currentTimeMillis()) {
          dropToken(token.token)
          val newToken = generateToken(key)
          tokensDao.createToken(newToken).map(_ => newToken)
        } else {
          Future(token)
        }
      case None =>
        val newToken = generateToken(key)
        tokensDao.createToken(generateToken(key)).map(_ => newToken)
    }
  }

  private def dropToken(key: TokenStr) = {
    tokensDao.deleteToken(key.tokenStr)
  }


  private def generateToken(key: String) = Token(generateTokenStr, maxTTL, key)

  private def generateTokenStr: TokenStr = TokenStr(UUID.randomUUID().toString)

  private def maxTTL: Long = System.currentTimeMillis() + contexts.tokenTTL
}
