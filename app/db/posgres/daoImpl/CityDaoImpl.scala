package db.posgres.daoImpl

import db.posgres.dao.CityDao
import db.posgres.models.{City, Location, Tariff}
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
    def id = column[Long]("cityid", O.PrimaryKey, O.AutoInc)

    def id_location = column[Long]("locationid")

    def id_tariff = column[Long]("tariffid")

    def location = foreignKey("locationid", id, locationDaoImpl.locations)(_.id)

    def tariff = foreignKey("tariffid", id, tariffDaoImp.tariffs)(_.id)

    def name = column[String]("name")

    def country = column[String]("country")

    override def * = (id, id_location, id_tariff, name, country) <> (City.tupled, City.unapply)

  }

  implicit val cities: TableQuery[CityTable] = TableQuery[CityTable]

  override def add(city: City): Future[Long] = {
    println(city)
    db.run(cities returning cities.map(_.id) += city)
  }

  override def get(id: Long): Future[Option[(City, Location, Tariff)]] = {
    val x = cities.filter(_.id === id)
      .join(locationDaoImpl.locations).on(_.id_location === _.id)
      .join(tariffDaoImp.tariffs).on(_._1.id_tariff === _.id)
      .map(res => (res._1._1, res._1._2, res._2))
    db.run(x.result.headOption)
  }

  override def delete(id: Long): Future[Int] = {
    db.run(cities.filter(_.id === id).delete)
  }

  override def getCount: Future[Int] = {
    db.run(cities.length.result)
  }

  override def getAll: Future[Seq[(City, Location, Tariff)]] = {
    val x = cities
      .join(locationDaoImpl.locations).on(_.id_location === _.id)
      .join(tariffDaoImp.tariffs).on(_._1.id_tariff === _.id)
      .map(res => (res._1._1, res._1._2, res._2))
    db.run(x.result)
  }

  override def getWithOffset(batchNumber: Int, batchSize: Int): Future[Seq[(City, Location, Tariff)]] = {
    val x = cities.drop(batchSize * batchNumber).take(batchSize)
      .join(locationDaoImpl.locations).on(_.id_location === _.id)
      .join(tariffDaoImp.tariffs).on(_._1.id_tariff === _.id)
      .map(res => (res._1._1, res._1._2, res._2))
    db.run(x.result)
  }
}
