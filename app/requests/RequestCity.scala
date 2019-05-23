package requests

import db.posgres.models.{City, Location, Tariff}

case class RequestCity(
                        id: Option[Long],
                        name: String,
                        country: String,
                        location: RequestLocation,
                        tariff: RequestTariff
                      )

object RequestCity {

  def apply(city: City, Location: Location, tariff: Tariff): RequestCity =
    RequestCity(Some(city.id), city.name, city.country,
      RequestLocation(Location.longitude, Location.longitude),
      RequestTariff(tariff.price, tariff.currency))
  
}
