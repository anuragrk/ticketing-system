package services

import java.util.UUID

import com.escalatesoft.subcut.inject.AutoInjectable
import daos.TicketsDAO
import models.Ticket
import reactivemongo.core.commands.LastError

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by Anurag.
 */
class TicketsService extends AutoInjectable {

  private val ticketsDAO = inject[TicketsDAO]
  private implicit val ec = inject[ExecutionContext]

  def createTicket(ticket: Ticket): Future[Ticket] =
    ticketsDAO.insert(ticket)

  def updateTicket(ticket: Ticket): Future[Ticket] =
    ticketsDAO.update(ticket)

  def deleteTicket(id:UUID): Future[LastError] =
    ticketsDAO.delete(id)

  def findTicket(id: UUID): Future[Option[Ticket]] =
    ticketsDAO.find(id)

  def findAll: Future[Seq[Ticket]] =
    ticketsDAO.findAll

  def findByStatus(status: String): Future[Seq[Ticket]] =
    ticketsDAO.findByStatus(status)

}
