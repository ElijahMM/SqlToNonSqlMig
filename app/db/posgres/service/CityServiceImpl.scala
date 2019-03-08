package db.posgres.service

import java.sql.Timestamp
import java.time.LocalDateTime

import db.posgres.dao.{CityDao, LocationDao, TariffDao}
import db.posgres.models.{City, Location, Tariff}
import javax.inject.{Inject, Singleton}
import requests.RequestCity

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class CityServiceImpl @Inject()(cityDao: CityDao, tariffDao: TariffDao, locationDao: LocationDao)(implicit ec: ExecutionContext) extends CityService {

  override def addCity(city: RequestCity): Future[Long] = {
    val futures = for {
      locationId <- locationDao.add(Location(0, city.location.latitude, city.location.longitude))
      tariffId <- tariffDao.add(Tariff(0, city.tariff.price, city.tariff.currency, Timestamp.valueOf(LocalDateTime.now())))
    } yield (locationId, tariffId)

    val insertResult = futures.map {
      case (locationId: Long, tariffId: Long) =>
        cityDao.add(City(0, locationId, tariffId, city.name, city.country))
    }

    Await.result(insertResult, Duration.Inf)
  }

  override def getCity(id: Long): Future[Option[RequestCity]] = ???

  override def deleteCity(id: Long): Future[Int] = ???

  override def getAllCities: Future[Seq[RequestCity]] = ???
}