package utils

import models._
import play.api.libs.json.Json

object json {

  trait TicketFormats {
    implicit val customerDetailFormat = Json.format[CustomerDetail]
    implicit val employeeFormat = Json.format[Employee]
    implicit val ticketFormat = Json.format[Ticket]
  }

}