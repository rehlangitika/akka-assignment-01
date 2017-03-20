package countingwords

import akka.actor.ActorSystem
import akka.testkit.TestKit
import edu.knoldus.countingwords.{ChildActor, ReadLine}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class ChildActorSpec extends TestKit(ActorSystem("WordCounting")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A Child Actor" must {
    "send a message to another actor when it is finished processing" in {
      val props = ChildActor.childProps
      val ref = system.actorOf(props)
      ref ! ReadLine("adjkl alkdlk asksrkrj aldflk")
      expectMsgPF() {
        case count: Int =>
          count must be(4)
      }
    }
  }

}
