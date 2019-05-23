package db.posgres.service

import java.sql.Timestamp
import java.time.LocalDateTime

import db.posgres.dao.{CityDao, LocationDao, TariffDao}
import db.posgres.models.{City, Location, Tariff}
import javax.inject.{Inject, Singleton}
import requests.{RequestCity, RequestLocation, RequestTariff}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class CityServiceImpl @Inject()(cityDao: CityDao, tariffDao: TariffDao, locationDao: LocationDao)(implicit ec: ExecutionContext) extends CityService {

  override def addCity(city: RequestCity): Future[Long] = {
    for {
      locationId <- locationDao.add(Location(0, city.location.latitude, city.location.longitude))
      tariffId <- tariffDao.add(Tariff(0, city.tariff.price, city.tariff.currency, Timestamp.valueOf(LocalDateTime.now())))
      result <- cityDao.add(City(0, locationId, tariffId, city.name, city.country))
    } yield result
  }

  override def getCity(id: Long): Future[Option[RequestCity]] = {
    cityDao.get(id).map( rez => {
      rez.map(r => RequestCity(r._1, r._2, r._3))
    })
  }

  override def deleteCity(id: Long): Future[Int] = ???

  override def getAllCities: Future[Seq[RequestCity]] = {
    cityDao.getAll.map { res =>
      res.seq.map(it => RequestCity(it._1, it._2, it._3))
    }
  }

  override def getCount: Future[Int] = {
    cityDao.getCount
  }


  override def getBatchCities(batchNumber: Int, batchSize: Int): Future[Seq[RequestCity]] = {
    println(s"Getting batch no $batchNumber with size $batchSize")
    cityDao.getWithOffset(batchNumber, batchSize).map { res =>
      res.seq.map(it => RequestCity(it._1, it._2, it._3))
    }
  }
}