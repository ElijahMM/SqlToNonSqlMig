package db.posgres.dao

import com.google.inject.ImplementedBy
import db.posgres.daoImpl.CityDaoImpl
import db.posgres.models.City
import requests.RequestCity

import scala.concurrent.Future

@ImplementedBy(classOf[CityDaoImpl])
trait CityDao {
  def add(city: City): Future[Long]

  def get(id: Long): Future[Option[City]]

  def delete(id: Long): Future[Int]

  def getAll: Future[Seq[City]]
}
