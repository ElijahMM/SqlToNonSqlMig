package db.posgres.models

case class City(
                 id: Long,
                 id_location: Long,
                 id_tariff: Long,
                 name: String,
                 country: String)

