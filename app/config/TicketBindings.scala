package config

import play.api.Play.current
import play.api.libs.concurrent.Execution

import scala.concurrent.ExecutionContext

import com.escalatesoft.subcut.inject.{BindingId, NewBindingModule}
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection

import daos._
import services._

object TicketBindingKeys {
  object TicketsCollection extends BindingId
}

object TicketBindings extends NewBindingModule(module => {

  import module._

  import TicketBindingKeys._

  bind[ExecutionContext] toSingle Execution.Implicits.defaultContext

  bind[JSONCollection] idBy TicketsCollection toSingle ReactiveMongoPlugin.db.collection[JSONCollection]("tickets")

  bind[TicketsDAO] toModuleSingle { implicit module => new TicketsDAO() }

  bind[TicketsService] toModuleSingle { implicit module => new TicketsService() }

})
