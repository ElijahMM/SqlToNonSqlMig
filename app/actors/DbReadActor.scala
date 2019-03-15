package actors


import actors.Notifiers.{GetData, SendData}
import akka.actor.{Actor, ActorLogging, Props}
import db.posgres.service.CityService

import scala.concurrent.ExecutionContext

class DbReadActor (cityService: CityService, batchSize: Int) extends Actor with ActorLogging {

  override def preStart(): Unit = log.info("DbReadActor started")

  override def postStop(): Unit = log.info("DbReadActor stopped")

  override def receive: Receive = {
    case GetData =>
//      cityService.getBatchCities(batchSize).map {
//        rez => sender() ! SendData(rez)
//      }
  }

}
