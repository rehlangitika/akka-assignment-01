package edu.knoldus.countingwords

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object WordCounter extends App {

  implicit val timeout = Timeout(1000.seconds)
  val actorSystem = ActorSystem("WordCounter")
  val child = actorSystem.actorOf(ChildActor.childProps)
  //actorSystem.actorOf(ChildActor.childProps)
  val props = ParentActor.parentProps(child, "./src/main/resources/demo.txt")
  //Props(classOf[ParentActor], child, "./src/main/resources/demo.txt")
  val actor = actorSystem.actorOf(props)
  val future = actor ? StartReading()
  future.map {
    result => println(s"Total No. Of Words : $result")
  }

}
