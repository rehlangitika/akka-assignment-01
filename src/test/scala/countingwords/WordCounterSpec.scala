package countingwords

import akka.actor.ActorSystem
import akka.routing.FromConfig
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import edu.knoldus.countingwords.{ChildActor, ParentActor, StartReading}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

import scala.concurrent.duration._

class WordCounterSpec extends TestKit(ActorSystem("WordCounter")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  //def this() = this(ActorSystem("WordCounter"))

  implicit val timeout = Timeout(1000.seconds)
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

  "A word counter helper" should {
    "return the count of the no of words from file" in {
      val system = ActorSystem("WordCounter", config)
      val child = system.actorOf(FromConfig.props(ChildActor.childProps), "poolRouter")
      val props = ParentActor.parentProps(child, "./src/main/resources/demo.txt")
      val actor = system.actorOf(props)
      actor ! StartReading()
      /*future.map {
        result: Int => {
          println(result)
          expectMsg()
        }
      }*/
      expectMsg(18)
    }
  }
}
