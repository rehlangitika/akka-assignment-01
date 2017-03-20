package edu.knoldus.countingwords

import akka.actor.{Actor, ActorLogging, Props}


class ChildActor extends Actor with ActorLogging {

  override def receive = {
    case ReadLine(line) =>
      val wordsInLine = line.split(" ").length
      sender() forward ProcessedLine(wordsInLine)

    case msg => log.info(s"Error! $msg")
  }

}

object ChildActor {
  def childProps = Props(classOf[ChildActor])
}



