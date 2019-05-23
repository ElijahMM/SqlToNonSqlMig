package controllers

import actors.Trigger
import db.DBModelToMongoDoc
import db.mongo.MongoDatabaseConfig
import db.posgres.models.Location
import db.posgres.service.CityService
import helpers.ProcessingQueue
import javax.inject.{Inject, Singleton}
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.{BsonDocument, BsonInt32}
import play.api.mvc._
import requests.{RequestCity, RequestLocation, RequestTariff}

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, cityService: CityService, mongoDatabaseConfig: MongoDatabaseConfig, trigger: Trigger)(implicit ec: ExecutionContext) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { implicit request =>
    Ok("App is ready")
  }

  def saveCity = Action { implicit request =>
    val requestCityData = RequestCity(Some(1),
      "Suceava",
      "Romania",
      RequestLocation(111.3, 111.1),
      RequestTariff(1000, "$")
    )
    val y = DBModelToMongoDoc.convert(requestCityData)
    val client = MongoClient(s"mongodb://localhost:27017")
    client.getDatabase("aaaaaa").getCollection("data").insertOne(y)
    Ok("")
  }

  def get(id: Option[Long]) = Action.async {
    val result = cityService.getCity(id.get)
    result.map(i =>
      if (i.isDefined)
        Ok("Got result: " + i.get)

      else {
        BadRequest(s"No city with id ${
          id.get
        }")
      }
    )
  }

  def getAll() = Action {
    val collection = mongoDatabaseConfig.getCollection("mongoTest")
    val document: BsonDocument = new BsonDocument("_id", new BsonInt32(2)).append("x", new BsonInt32(1))
    collection.insertOne(document)
    Ok(views.html.index("Your new application is ready."))
  }

  def startProcessing = Action {
    val processingQueue = new ProcessingQueue
    cityService.getCount map { rez =>
      processingQueue.totalNumber = rez
      trigger.startProcessing(processingQueue)
    }
    Ok("1")
  }

}
