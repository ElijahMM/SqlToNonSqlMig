package changelog

import com.github.mongobee.changeset.{ChangeLog, ChangeSet}
import com.typesafe.config.ConfigFactory
import db.mongo.MongoDatabaseConfig
import javax.inject.Inject
import org.mongodb.scala.MongoClient
import org.mongodb.scala.Document

@ChangeLog
class DataBaseChangelog @Inject()(val x:MongoDatabaseConfig){

  @ChangeSet(order = "001", id = "1", author = "em")
  def work(db: MongoClient): Unit = {
    println("I am here")

    x.client().getDatabase("thingpark_hub").getCollection("data").insertOne(Document("int" -> "1"))
  }
}
