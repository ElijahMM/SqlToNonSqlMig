package db.posgres.dao

import com.google.inject.ImplementedBy
import db.posgres.daoImpl.LocationDaoImpl
import db.posgres.models.Location

import scala.concurrent.Future

@ImplementedBy(classOf[LocationDaoImpl])
trait LocationDao {
  def add(location: Location): Future[Long]

  def get(id: Long): Future[Option[Location]]

  def delete(id: Long): Future[Int]

  def getAll: Future[Seq[Location]]
}
