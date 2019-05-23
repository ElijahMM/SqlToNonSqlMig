package actors


import akka.actor.Actor
import helpers.Notifiers.{GotData, ReadData}
import stages.ReadStage

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class DbReadActor(readStage: ReadStage) extends Actor {

  override def receive: Receive = {
    case ReadData(processingQueue) => {
      val result = readStage.execute(processingQueue)
      Await.result(result, Duration.Inf)
      sender() ! GotData(processingQueue)
    }
  }

}
