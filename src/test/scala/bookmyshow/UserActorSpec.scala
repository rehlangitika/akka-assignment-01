package bookmyshow

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import edu.knoldus.bookmyshow.{BookSeat, UserActor}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class UserActorSpec extends TestKit(ActorSystem("bookmyshow")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A UserActor" must {
    "send a message to another actor when it finished processing" in {
      val props = UserActor.userProps(testActor)
      val ref = system.actorOf(props)

      ref ! BookSeat("A")
      expectMsg(BookSeat("A"))
    }
  }

}
