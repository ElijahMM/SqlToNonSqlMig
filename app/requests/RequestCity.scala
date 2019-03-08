package requests

case class RequestCity(
                        name: String,
                        country: String,
                        location: Location,
                        tariff: Tariff
                      )
