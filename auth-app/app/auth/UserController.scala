package auth

import com.nongped.microservices.auth.User
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import tokens.TokenService
import utils.Contexts

import scala.concurrent.Future

@Singleton
class UserController @Inject()(tokenService: TokenService, contexts: Contexts, cc: ControllerComponents)
  extends AbstractController(cc) {

  implicit val executionContext = contexts.cpuLookup

  def register = Action.async(parse.json) { implicit request =>
    request.body.validate[User].fold(
      error => Future.successful(BadRequest(s"Not a valid input format: ${error.mkString}")),
      user => tokenService.createToken(user.email).map(newToken => Ok(Json.toJson(newToken.token)))
    )
  }
}
