package actors

import requests.RequestCity

object Notifiers {

  final case class GetData(batchSize: Int)

  final case class SendData(data: Seq[RequestCity])

  final case class SendLog(data: Seq[RequestCity])

  final case class StarProcessing(data: Seq[RequestCity])

}
