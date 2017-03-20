package edu.knoldus.countingwords

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object WordCounter extends App {

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   resizer {
      |      pressure-threshold = 0
      |      lower-bound = 1
      |      upper-bound = 15
      |      messages-per-resize = 1
      |    }
      | }
      |}
    """.stripMargin
  )

  implicit val timeout = Timeout(1000.seconds)
  val actorSystem = ActorSystem("WordCounter", config)
  val child = actorSystem.actorOf(FromConfig.props(ChildActor.childProps), "poolRouter")
  //actorSystem.actorOf(FromConfig.props(Props[ChildActor]), "poolRouter")
  val props = ParentActor.parentProps(child, "./src/main/resources/demo.txt")
  //Props(classOf[ParentActor], child, "./src/main/resources/demo.txt")
  val actor = actorSystem.actorOf(props)
  val future = actor ? StartReading()
  future.map {
    result => println(s"Total No. Of Words : $result")
  }

}
