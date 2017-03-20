package edu.knoldus.bookmyshow

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object BookMyShow extends App {
  implicit val timeout = Timeout(1000.seconds)
  val actorSystem = ActorSystem("BookMyShow")
  val occupyRef = actorSystem.actorOf(OccupySeat.occupyProps)
  //actorSystem.actorOf(Props(classOf[OccupySeat]))
  val userActorProps = UserActor.userProps(occupyRef)
  //actorSystem.actorOf(Props(classOf[UserActor], occupyRef))
  val userRef = actorSystem.actorOf(userActorProps)
  val seatStatus = userRef ? BookSeat("A")
  seatStatus.foreach(println)
  val seatStatus1 = userRef ? BookSeat("A")
  seatStatus1.foreach(println)

}
