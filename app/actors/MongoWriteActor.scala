package actors

import akka.actor.Actor
import helpers.Notifiers.{DoneProcessing, Log, ReadData, WriteData}
import stages.WriteStage

class MongoWriteActor(writeStage: WriteStage) extends Actor {


  override def receive: Receive = {
    case WriteData(processingQueue) => {
      val result = writeStage.execute(processingQueue)
      if (result) {
        sender ! ReadData(processingQueue)
      } else {
        sender ! DoneProcessing(processingQueue)
      }
    }
    case Log(queue) => writeStage.log(queue)
  }

}
