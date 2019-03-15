package db.posgres.dao

import com.google.inject.ImplementedBy
import db.posgres.daoImpl.CityDaoImpl
import db.posgres.models.{City, Tariff, Location}

import scala.concurrent.Future

@ImplementedBy(classOf[CityDaoImpl])
trait CityDao {
  def add(city: City): Future[Long]

  def get(id: Long): Future[Option[(City, Location, Tariff)]]

  def delete(id: Long): Future[Int]

  def getAll: Future[Seq[(City, Location, Tariff)]]

  def getWithOffset(batchNumber: Int, batchSize: Int): Future[Seq[(City, Location, Tariff)]]
}
