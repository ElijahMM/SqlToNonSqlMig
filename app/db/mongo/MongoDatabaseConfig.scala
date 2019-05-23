package db.mongo

import com.typesafe.config.ConfigFactory
import javax.inject.Singleton
import org.mongodb.scala.MongoClient

@Singleton
class MongoDatabaseConfig {

  def client(): MongoClient = {
    MongoClient(s"mongodb://dev_root:toors@localhost:27017/thingpark_hub")
  }


  def getCollection(name: String) = client().getDatabase("thingpark_hub").getCollection(name)

}

