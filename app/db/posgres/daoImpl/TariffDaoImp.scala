package db.posgres.daoImpl

import java.sql.Timestamp

import db.posgres.dao.TariffDao
import db.posgres.models.Tariff
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class TariffDaoImp @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends TariffDao {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._

  class TariffTable(tag: Tag) extends Table[Tariff](tag, "tariffs") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def price = column[Long]("price")

    def currency = column[String]("currency")

    def year = column[Timestamp]("year", O.SqlType("timestamp default now()"))

    override def * = (id, price, currency, year) <> (Tariff.tupled, Tariff.unapply)
  }


  implicit val tariffs: TableQuery[TariffTable] = TableQuery[TariffTable]

  override def add(tariff: Tariff): Future[Long] = {
    db.run(tariffs returning tariffs.map(_.id) += tariff)
  }

  override def get(id: Long): Future[Option[Tariff]] = {
    db.run(tariffs.filter(_.id === id).result.headOption)
  }

  override def delete(id: Long): Future[Int] = {
    db.run(tariffs.filter(_.id === id).delete)
  }

  override def getAll: Future[Seq[Tariff]] = {
    db.run(tariffs.result)
  }
}
