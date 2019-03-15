package requests

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class RequestLocation(
                            latitude: Double,
                            longitude: Double
                          )


object RequestLocation {

  val requestLocationForm = Form(
    mapping(
      "latitude" -> of(doubleFormat),
      "longitude" -> of(doubleFormat)
    )(RequestLocation.apply)(RequestLocation.unapply))
}