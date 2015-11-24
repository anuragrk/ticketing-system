package models

import play.api.data._
import play.api.data.Forms._

import java.util.UUID


trait FormConstraints {

  import play.api.data.validation._

  private val spacesRegex = """\s.*""".r

  val mustBeUUID: Constraint[String] = Constraint[String]("must.be.uuid")({ uuid =>
    scala.util.control.Exception.allCatch.opt(UUID.fromString(uuid)) match {
      case Some(uuid) => Valid
      case None => Invalid("Must be a UUID")
    }
  })

  val mustNotContainSpaces: Constraint[String] = Constraint[String]("must.not.contain.spaces")({ string =>
    if (spacesRegex.findAllMatchIn(string).nonEmpty) {
      Invalid("Must not contain spaces")
    } else {
      Valid
    }
  })

}


object TicketForms extends FormConstraints {


  case class TicketRequest(
                                 customerName: String,
                                 customerPhoneNumber: String,
                                 comments: String,
                                 createdBy: String,
                                 assignedTo: String,
                                 status: String,
                                 area: String
                                 )

  val ticketRequestForm = Form(
    mapping(
      "customerName" -> nonEmptyText,
      "customerPhoneNumber" -> nonEmptyText,
      "comments" -> nonEmptyText,
      "createdBy" -> nonEmptyText,
      "assignedTo" -> nonEmptyText,
      "status" -> nonEmptyText,
      "area" -> nonEmptyText
    )(fromTicketRequestForm)(toTicketRequestForm)
  )

   def fromTicketRequestForm(customerName: String,customerPhoneNumber: String, comments: String, createdBy: String, assignedTo: String, status: String, area: String) =
    TicketRequest(customerName = customerName, customerPhoneNumber = customerPhoneNumber, comments = comments, createdBy =  createdBy, assignedTo = assignedTo, status = status, area = area)

   def toTicketRequestForm(request: TicketRequest) =
    Option(
      (request.customerName,request.customerPhoneNumber,request.comments, request.createdBy, request.assignedTo, request.status, request.area)
    )

}


