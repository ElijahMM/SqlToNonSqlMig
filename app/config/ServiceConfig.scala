package config

import com.github.mongobee.Mongobee
import com.typesafe.config.ConfigFactory
import javax.inject.Singleton

@Singleton
class ServiceConfig() {

  def a(): Unit = {
    val runner = new Mongobee(s"mongodb://dev_root:toors@localhost:27017/thingpark_hub")
    runner
      .setDbName("thingpark_hub")
      .setChangeLogsScanPackage("changelog")
      .execute()
  }
}
