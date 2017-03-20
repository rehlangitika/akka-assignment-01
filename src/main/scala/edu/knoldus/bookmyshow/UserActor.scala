package edu.knoldus.bookmyshow

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class UserActor(occupyActor: ActorRef) extends Actor with ActorLogging {

  override def receive = {
    case BookSeat(seat) =>
      log.info(s"Booking Seats, Seats: $seat")
      occupyActor.forward(BookSeat(seat))
    case msg =>
      log.info("User: Sorry! Invalid Request!")
  }

}

object UserActor {
  def userProps(occupyActor: ActorRef) = Props(classOf[UserActor], occupyActor)
}

