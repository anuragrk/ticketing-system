package controllers

import models._
import services._
import utils.json._

import play.api.mvc._
import play.api.data._

import com.escalatesoft.subcut.inject.AutoInjectable

import scala.concurrent.{ExecutionContext, Future}

import java.util.UUID

class TicketsController extends Controller with AutoInjectable with TicketFormats {

  private implicit val ec = inject[ExecutionContext]
  val ticketsService = inject[TicketsService]


  def list() = Action.async { implicit request =>
    for {
      tickets <- ticketsService.findAll
    } yield {
      Ok(views.html.tickets.list(tickets))
    }
  }

  def read(id: UUID) = Action.async { implicit request =>
    for {
      ticket <- ticketsService.findTicket(id)
    } yield {
      ticket match {
        case Some(ticket) =>
          Ok(views.html.tickets.ticket(TicketForms.ticketRequestForm.fill(toTicketRequest(ticket)), routes.TicketsController.edit(id),
            routes.TicketsController.list))
        case None =>
          NotFound
      }
    }
  }

  def showCreate() = Action.async{ implicit request =>
    Future.successful(Ok(createTicketView(TicketForms.ticketRequestForm)))
  }

  def create = Action.async { implicit request =>
    TicketForms.ticketRequestForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(createTicketView(formWithErrors))),
      ticketRequest => {
        val ticket = toTicket(ticketRequest)
        ticketsService.createTicket(ticket).map { ticket =>
          Redirect(routes.TicketsController.list).flashing("success" -> s"Ticket ${ticket._id} created")
        }
      }
    )
  }

  private def createTicketView(form: Form[TicketForms.TicketRequest])(implicit request: RequestHeader) =
    views.html.tickets.ticket(form, routes.TicketsController.create,
      routes.TicketsController.list)

  def showEdit(id: UUID) = Action.async { implicit request =>
    ticketsService.findTicket(id).map {
      case Some(ticket) =>
        val ticketRequest = toTicketRequest(ticket)
        Ok(editTicketView(id, TicketForms.ticketRequestForm.fill(ticketRequest)))
      case None =>
        NotFound
    }
  }

  def edit(id: UUID) = Action.async { implicit request =>
    TicketForms.ticketRequestForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(editTicketView(id, formWithErrors))),
      ticketRequest =>
        ticketsService.updateTicket(toTicket(ticketRequest).copy(_id = id)).map { ticket =>
            Redirect(routes.TicketsController.list).flashing("success" -> s"Ticket ${id} updated")
        }
    )
  }

  private def editTicketView(id: UUID, form: Form[TicketForms.TicketRequest])(implicit request: RequestHeader) =
    views.html.tickets.ticket(form, routes.TicketsController.edit(id),
      routes.TicketsController.list)


  private def toTicketRequest(ticket: Ticket) = {
    TicketForms.TicketRequest(
      customerName= ticket.customerInfo.name,
      customerPhoneNumber = ticket.customerInfo.contactNumber,
      comments = ticket.comments,
      createdBy = ticket.createdBy.name,
      assignedTo = ticket.assignedTo.name,
      status = ticket.status,
      area = ticket.area
    )
  }

  private def toTicket(ticketRequest: TicketForms.TicketRequest) = {
    Ticket(
      customerInfo = CustomerDetail(ticketRequest.customerName,ticketRequest.customerPhoneNumber),
      comments = ticketRequest.comments,
      createdBy = Employee(ticketRequest.createdBy,ticketRequest.createdBy),
      assignedTo = Employee(ticketRequest.assignedTo,ticketRequest.assignedTo),
      status = ticketRequest.status,
      area = ticketRequest.area
    )
  }

}
