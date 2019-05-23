package helpers

object Notifiers {

  class Notifier()

  final case class ReadData(processingQueue: ProcessingQueue) extends Notifier

  final case class WriteData(processingQueue: ProcessingQueue) extends Notifier

  final case class GotData(processingQueue: ProcessingQueue) extends Notifier

  final case class StarProcessing(processingQueue: ProcessingQueue) extends Notifier

  final case class DoneProcessing(processingQueue: ProcessingQueue) extends Notifier

  final case class Log(processingQueue: ProcessingQueue) extends Notifier

}
