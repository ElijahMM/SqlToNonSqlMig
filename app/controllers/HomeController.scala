package controllers

import db.mongo.MongoDatabaseConfig
import db.posgres.service.CityService
import javax.inject._
import play.api.mvc._
import requests.RequestCity

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, cityService: CityService, mongoDatabaseConfig: MongoDatabaseConfig)(implicit ec: ExecutionContext) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { implicit request =>
    Ok(views.html.city(RequestCity.requestCityForm))
  }

  def saveCity = Action(parse.form(RequestCity.requestCityForm)) { implicit request =>
    val requestCityData = request.body

    for (_ <- 1 to 10000)
      cityService.addCity(requestCityData)

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

  def getAll() = Action.async {
    val result = cityService.getAllCities
    result.map(i =>
      Ok("Got result: " + i)

    )
  }

}
