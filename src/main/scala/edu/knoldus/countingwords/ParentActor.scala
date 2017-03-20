package edu.knoldus.countingwords

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.io.Source.fromFile

class ParentActor(childRef: ActorRef, fileName: String) extends Actor with ActorLogging {
  var running = false
  var totalLines = 0
  var linesRead = 0
  var senderActor: Option[ActorRef] = None
  private var count = 0

  override def receive = {
    case StartReading() =>
      if (running)
        log.info("Error! Already started!")
      else {
        running = true
        senderActor = Some(sender)
        fromFile(fileName).getLines.foreach {
          line =>
            childRef ! ReadLine(line)
            totalLines += 1
        }
      }
    case ProcessedLine(words) =>
      count += words
      linesRead += 1
      if (linesRead == totalLines) {
        senderActor.map(_ ! count)
      }
    case _ => log.info("message not recognised!")
  }

}

object ParentActor {
  def parentProps(childRef: ActorRef, fileName: String) = Props(classOf[ParentActor],childRef,fileName)
}
