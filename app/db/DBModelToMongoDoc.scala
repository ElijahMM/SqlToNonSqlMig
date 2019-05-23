package db

import org.mongodb.scala.Document
import requests.RequestCity

object DBModelToMongoDoc {

  def convert(requestCity: RequestCity): Document = {
    Document(
      "tariff" -> Document(
        "_id" ->requestCity.id.get,
        "city" -> Document(
          "name" -> requestCity.name,
          "country" -> requestCity.country,
          "location" -> Document(
            "latitude" -> requestCity.location.latitude,
            "longitude" -> requestCity.location.longitude
          )
        )
      )
    )
  }
}
