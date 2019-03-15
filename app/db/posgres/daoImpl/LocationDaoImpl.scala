package db.posgres.daoImpl

import db.posgres.dao.LocationDao
import db.posgres.models.Location
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class LocationDaoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends LocationDao {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._

  class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
    def id = column[Long]("locationid", O.PrimaryKey, O.AutoInc)

    def latitude = column[Double]("latitude")

    def longitude = column[Double]("longitude")

    override def * = (id, latitude, longitude) <> (Location.tupled, Location.unapply)

  }


  implicit val locations: TableQuery[LocationTable] = TableQuery[LocationTable]


  override def add(location: Location): Future[Long] = {
    db.run(locations returning locations.map(_.id) += location)
  }

  override def get(id: Long): Future[Option[Location]] = {
    db.run(locations.filter(_.id === id).result.headOption)
  }

  override def delete(id: Long): Future[Int] = {
    db.run(locations.filter(_.id === id).delete)
  }

  override def getAll: Future[Seq[Location]] = {
    db.run(locations.result)
  }
}
