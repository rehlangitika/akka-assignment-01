package edu.knoldus.bookmyshow

import akka.actor.{Actor, ActorLogging, Props}


class OccupySeat extends Actor with ActorLogging {

  private var seatList = List("A", "B", "C", "D", "E", "F")

  override def receive = {
    case BookSeat(seat) =>
      log.info(s"Checking for the seat availability Seats: $seat")
      if (seatList.contains(seat)) {
        seatList = seatList diff List(seat)
        sender() ! s"Success: We booked $seat seats for you! Seats Left = $seatList"
      }
      else {
        sender() ! s"Better Luck Next Time! :("
      }
    case msg =>
      log.info("Occupy: Sorry! Invalid Request!")
  }

}



object OccupySeat {
  def occupyProps = Props(classOf[OccupySeat])
}
