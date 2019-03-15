package requests

import play.api.data.Form
import play.api.data.Forms._


case class RequestTariff(
                          price: Long,
                          currency: String,
                        )

object RequestTariff {

  val requestTariffForm = Form(
    mapping(
      "price" -> longNumber,
      "currency" -> text
    )(RequestTariff.apply)(RequestTariff.unapply))
}