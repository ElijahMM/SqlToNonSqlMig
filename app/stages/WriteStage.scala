package stages

import com.typesafe.config.ConfigFactory
import db.DBModelToMongoDoc
import db.mongo.MongoDatabaseConfig
import helpers.ProcessingQueue
import javax.inject.{Inject, Singleton}
import org.mongodb.scala.{ClientSession, Completed, Document, MongoException, Observable, ReadConcern, SingleObservable, TransactionOptions, WriteConcern}
import requests.RequestCity

import scala.concurrent.ExecutionContext

@Singleton
class WriteStage @Inject()(mongoDatabaseConfig: MongoDatabaseConfig)(implicit ec: ExecutionContext) {

  val batchSize = ConfigFactory.load().getInt("dbproprieties.query.batchSize")
  val database = mongoDatabaseConfig.client().getDatabase("think_park")
  val collection = mongoDatabaseConfig.getCollection("mongoTest")
  val collectionLog = mongoDatabaseConfig.getCollection("mongoTestLog")

  def execute(processingQueue: ProcessingQueue): Boolean = {
    if (processingQueue.dataQueue.head.isEmpty) {
      false
    } else {
      val batch = processingQueue.dataQueue.dequeue()
      val documents = getDocumentsList(batch)
      collection.insertMany(documents).subscribe((x: Completed) => println(x))
      true
    }
  }

  def getDocumentsList(cities: Seq[RequestCity]) = {
    cities.map { c => DBModelToMongoDoc.convert(c) }
  }

  def log(processingQueue: ProcessingQueue): Unit = {
    println("Logging")

    def expectedBatchCount = processingQueue.totalNumber / batchSize

    def actualBatchNumber = processingQueue.batchNumber - 1

    val logMessage = Document(
      "expectedBatchCount" -> expectedBatchCount,
      "actualBatchNumber" -> actualBatchNumber,
      "percentage" -> ((actualBatchNumber / expectedBatchCount) * 100)
    )
    collectionLog.insertOne(logMessage).subscribe((x: Completed) => println(x))
  }

}
