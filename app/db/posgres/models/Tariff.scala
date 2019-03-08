package db.posgres.models

import java.sql.Timestamp


case class Tariff(
                   id: Long,
                   price: Long,
                   currency: String,
                   year: Timestamp)
