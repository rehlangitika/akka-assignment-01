package bookmyshow

import akka.actor.ActorSystem
import akka.testkit.TestKit
import edu.knoldus.bookmyshow.{BookSeat, OccupySeat}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class OccupySeatSpec extends TestKit(ActorSystem("bookmyshow")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "An OccupySeat Actor" must {
    "send a message to another actor when it is finished processing" in {
      val props = OccupySeat.occupyProps
      val ref = system.actorOf(props)
      ref ! BookSeat("A")
      expectMsgPF() {
        case s: String =>
          s must be(s"Success: We booked A seats for you! Seats Left = List(B, C, D, E, F)")
      }
    }
  }
}
