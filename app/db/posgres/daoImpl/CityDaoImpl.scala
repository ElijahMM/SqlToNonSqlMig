package db.posgres.daoImpl

import db.posgres.dao.CityDao
import db.posgres.models.City
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}

class CityDaoImpl @Inject()(dbConfigProvider: DatabaseConfigProvider, val locationDaoImpl: LocationDaoImpl, val tariffDaoImp: TariffDaoImp)(implicit ec: ExecutionContext) extends CityDao {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._

  class CityTable(tag: Tag) extends Table[City](tag, "cities") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def id_location = column[Long]("locationId")

    def id_tariff = column[Long]("tariffId")

    def location = foreignKey("LOC_FK", id_location, locationDaoImpl.locations)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def tariff = foreignKey("TARIFF_FK", id_tariff, tariffDaoImp.tariffs)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def name = column[String]("name")

    def country = column[String]("country")

    override def * = (id, id_location, id_tariff, name, country) <> (City.tupled, City.unapply)

  }

  implicit val cities: TableQuery[CityTable] = TableQuery[CityTable]

  override def add(city: City): Future[Long] = {
    db.run(cities returning cities.map(_.id) += city)
  }

  override def get(id: Long): Future[Option[City]] = {
    db.run(cities.filter(_.id === id).result.headOption)
  }

  override def delete(id: Long): Future[Int] = {
    db.run(cities.filter(_.id === id).delete)
  }

  override def getAll: Future[Seq[City]] = {
    db.run(cities.result)
  }
}
