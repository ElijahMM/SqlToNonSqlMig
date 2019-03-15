package db.mongo

import db.posgres.service.CityService
import javax.inject.{Inject, Singleton}
import play.api.Configuration

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class ReadDbWriteMongo @Inject()(cityService: CityService, mongoDatabaseConfig: MongoDatabaseConfig, configuration: Configuration)(implicit ec: ExecutionContext) {

  val batchSize = configuration.getOptional("dbproprieties.query.batchSize").getOrElse(15)
  val collection = mongoDatabaseConfig.getCollection("mongoTest")

  def getAllDBData = {
    Await.result(cityService.getAllCities, Duration.Inf)
  }

  def writeToMongoInBatch(): Unit = {

  }

}
