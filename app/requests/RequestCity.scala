package requests

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class RequestCity(
                        name: String,
                        country: String,
                        location: RequestLocation,
                        tariff: RequestTariff
                      )

object RequestCity {

  val requestCityForm = Form(
    mapping(
      "name" -> text,
      "country" -> text,
      "location" -> mapping(
        "latitude" -> of(doubleFormat),
        "longitude" -> of(doubleFormat)
      )(RequestLocation.apply)(RequestLocation.unapply),
      "tariff" -> mapping(
        "price" -> longNumber,
        "currency" -> text
      )(RequestTariff.apply)(RequestTariff.unapply)
    )(RequestCity.apply)(RequestCity.unapply))
}