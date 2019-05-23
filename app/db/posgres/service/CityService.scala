package db.posgres.service

import com.google.inject.ImplementedBy
import requests.RequestCity

import scala.concurrent.Future

@ImplementedBy(classOf[CityServiceImpl])
trait CityService {
  def addCity(city: RequestCity): Future[Long]

  def getCity(id: Long): Future[Option[RequestCity]]

  def deleteCity(id: Long): Future[Int]

  def getAllCities: Future[Seq[RequestCity]]

  def getCount: Future[Int]

  def getBatchCities(batchNumber: Int, batchSize: Int): Future[Seq[RequestCity]]
}
