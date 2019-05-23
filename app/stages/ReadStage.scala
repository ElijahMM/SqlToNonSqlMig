package stages

import com.typesafe.config.ConfigFactory
import db.posgres.service.CityService
import helpers.ProcessingQueue
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext

@Singleton
class ReadStage @Inject()(cityService: CityService)(implicit ec: ExecutionContext) {

  val batchSize = ConfigFactory.load().getInt("dbproprieties.query.batchSize")

  def execute(processingQueue: ProcessingQueue) = {
    cityService.getBatchCities(processingQueue.batchNumber ,batchSize).map { rez =>
      processingQueue.dataQueue.enqueue(rez)
      processingQueue.batchNumber += 1
    }
  }
}
