import play.api.mvc.WithFilters
import play.filters.gzip.GzipFilter

import config.TicketBindings
import utils.TicketingSystemGlobal

object Global extends WithFilters(new GzipFilter()) with TicketingSystemGlobal  {

  val bindingModule = TicketBindings

}

