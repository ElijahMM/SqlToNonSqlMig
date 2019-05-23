package helpers

import requests.RequestCity

import scala.collection.mutable

class ProcessingQueue {

  var batchNumber: Int = 0
  var totalNumber: Int = 0
  val dataQueue : mutable.Queue[Seq[RequestCity]] = mutable.Queue()
}
