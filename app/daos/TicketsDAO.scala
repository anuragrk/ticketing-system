package daos

import java.util.UUID

import com.escalatesoft.subcut.inject.AutoInjectable
import config.TicketBindingKeys.TicketsCollection
import models.Ticket
import play.api.libs.json.Json
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.core.commands.LastError
import utils.json.TicketFormats

import scala.concurrent.{ExecutionContext, Future}

class TicketsDAO extends AutoInjectable with TicketFormats {

  private def collection = inject[JSONCollection](TicketsCollection)

  private implicit val ec = inject[ExecutionContext]

  def insert(ticket: Ticket): Future[Ticket] =
    collection.insert(ticket).map(_ => ticket)

  def update(ticket: Ticket): Future[Ticket] =
    collection.update(Json.obj("_id" -> ticket._id),ticket).map(_ => ticket)

  def delete(id: UUID): Future[LastError] =
    collection.remove(Json.obj("_id" -> id))

  def find(id: UUID): Future[Option[Ticket]] =
    collection.find(Json.obj("_id" -> id)).one[Ticket]

  def findAll: Future[Seq[Ticket]] =
    collection.find(Json.obj()).cursor[Ticket].collect[Seq]()


  def findByStatus(status: String): Future[Seq[Ticket]] =
    collection.find(Json.obj( "status" -> status)).cursor[Ticket].collect[Seq]()

}
