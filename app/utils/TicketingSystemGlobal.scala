package utils

import play.api.GlobalSettings

import com.escalatesoft.subcut.inject.BindingModule

import config.TicketBindings

import scala.concurrent.Future

trait TicketingSystemGlobal extends GlobalSettings {

  def bindingModule: BindingModule

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    try {
      controllerClass.newInstance()
    }
    catch {
      case reflex: InstantiationException =>
        val injectedConstructor = controllerClass.getDeclaredConstructor(classOf[BindingModule])
        injectedConstructor.newInstance(bindingModule)
    }
  }

}
