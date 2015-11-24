package models

import java.util.UUID

case class CustomerDetail(
name: String,
contactNumber: String
)

case class Employee(employeeCode: String,name: String)

case class Ticket(
  _id: UUID = UUID.randomUUID,
  customerInfo: CustomerDetail,
  comments: String,
  createdBy: Employee,
  assignedTo: Employee,
  status: String,
  area: String
)

