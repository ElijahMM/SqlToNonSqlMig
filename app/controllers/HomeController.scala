package controllers

import db.mongo.MongoDatabaseConfig
import db.posgres.models.City
import db.posgres.service.CityService
import javax.inject._
import org.mongodb.scala.Completed
import org.mongodb.scala.bson.{BsonDocument, BsonInt32}
import play.api.mvc._
import requests.{Location, RequestCity, Tariff}

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, cityService: CityService, mongoDatabaseConfig: MongoDatabaseConfig)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    val collection = mongoDatabaseConfig.getCollection("mongoTest")
    val document: BsonDocument = new BsonDocument("_id", new BsonInt32(2)).append("x", new BsonInt32(1))
    collection.insertOne(document).subscribe((x: Completed) => println(x))

    Ok(views.html.index("Your new application is ready."))
  }


  def get = Action {
    val tariff = Tariff(11, "$")
    val location = Location(1111.1, 1111.0)
    val city = RequestCity("Suceava", "Romania", location, tariff)

    cityService.addCity(city).map {
      res => println(res)
    }
    Ok("1")
  }
}
