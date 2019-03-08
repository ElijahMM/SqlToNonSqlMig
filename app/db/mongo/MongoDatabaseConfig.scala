package db.mongo

import com.typesafe.config.ConfigFactory
import javax.inject.Singleton
import org.mongodb.scala.MongoClient

@Singleton
class MongoDatabaseConfig {

  private def client(): MongoClient = {
    val hostName = ConfigFactory.load().getString("mongo.host")
    MongoClient(s"mongodb://$hostName:27017")
  }

  private def getDatabase(name: String = ConfigFactory.load().getString("mongo.dbName")) = client().getDatabase(name)

  def getCollection(name: String) = getDatabase().getCollection(name)
}

