package actors

import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout
import helpers.Notifiers._
import stages.{ReadStage, WriteStage}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class OperationSupervisor(writeStage: WriteStage, readStage: ReadStage) extends Actor {

  private val mongoActor: ActorRef = context.actorOf(Props(classOf[MongoWriteActor], writeStage))

  private val postgresActor: ActorRef = context.actorOf(Props(classOf[DbReadActor], readStage))

  implicit val timeout: Timeout = Timeout(10 seconds)
  implicit val ec: ExecutionContext = ExecutionContext.global

  override def receive: PartialFunction[Any, Unit] = {
    case ReadData(queue) => postgresActor ! ReadData(queue)
    case GotData(queue) => mongoActor ! WriteData(queue)
    case DoneProcessing(queue) => mongoActor ! Log(queue)
  }
}