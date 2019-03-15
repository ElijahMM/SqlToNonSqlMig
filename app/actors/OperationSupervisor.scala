package actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.FromConfig
import db.posgres.service.CityService
import javax.inject.Inject
import org.apache.commons.lang3.exception.ExceptionContext


class OperationSupervisor @Inject()(cityService: CityService, batchSize: Int) extends Actor with ActorLogging {

  private val mongoRouter = context.actorOf(FromConfig.props(Props(classOf[MongoWriteActor])), "mongoRouter")

  private val postgressRouter = context.actorOf(FromConfig.props(Props(classOf[DbReadActor], cityService, batchSize)), "postgressRouter")

  override def preStart(): Unit = log.info("OperationSupervisor Application started")

  override def postStop(): Unit = log.info("OperationSupervisor Application stopped")

  // No need to handle any messages
  override def receive = Actor.emptyBehavior

}