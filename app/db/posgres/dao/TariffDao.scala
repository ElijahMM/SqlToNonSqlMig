package db.posgres.dao

import com.google.inject.ImplementedBy
import db.posgres.daoImpl.TariffDaoImp
import db.posgres.models.Tariff

import scala.concurrent.Future

@ImplementedBy(classOf[TariffDaoImp])
trait TariffDao {
  def add(tariff: Tariff): Future[Long]

  def get(id: Long): Future[Option[Tariff]]

  def delete(id: Long): Future[Int]

  def getAll: Future[Seq[Tariff]]
}
