package countingwords

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import edu.knoldus.countingwords.{ParentActor, ReadLine, StartReading}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class ParentActorSpec extends TestKit(ActorSystem("WordCounter")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A Parent Actor" must {
    "send a message to another actor when it is finished processing" in {
      val props = ParentActor.parentProps(testActor, "./src/main/resources/demo.txt")
      val ref = system.actorOf(props)
      ref ! StartReading()

      expectMsg(ReadLine("adjkl alkdlk asksrkrj aldflk"))
    }
  }

}
