package actors

import akka.actor.{ActorRef, ActorSystem, Props}
import helpers.Notifiers.ReadData
import helpers.ProcessingQueue
import javax.inject.{Inject, Singleton}
import stages.{ReadStage, WriteStage}

@Singleton
class Trigger @Inject()(actorSystem: ActorSystem, writeStage: WriteStage, readStage: ReadStage) {


  private val supervisor: ActorRef = actorSystem.actorOf(Props(classOf[OperationSupervisor],writeStage, readStage))

  def startProcessing(processingQueue: ProcessingQueue): Unit ={
    supervisor ! ReadData(processingQueue)
  }


}
