package controllers

import com.escalatesoft.subcut.inject.AutoInjectable
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class Application extends Controller with AutoInjectable {

  private implicit val ec = inject[ExecutionContext]

  def index = Action.async { implicit request =>
    Future.successful(Ok("Welcome to Customer Request System!"))
  }

}
