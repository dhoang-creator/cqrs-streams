import cats.effect.IO
import endless.core.interpret.EntityT

class BookingEntitySuite
  extends munit.CatsEffectSuite
  with munit.ScalaCheckEffectSuite
  with Generators {

  private val bookingAlg = BookingEntity(EntityT.instance[IO, BookingAlg, BookingEvent])
  private implicit val eventApplier: BookingEventApplier = new BookingEventApplier

  test("place booking") {
    forAllF { booking: Booking =>
      bookingAlg
        .place(booking)
        .run(None)
        .map {
          case Right((events, _)) =>
            assertEquals(
              events,
              Chain(BookingPlaced(booking))
            )
          case Left(error) => fail(error)
        }
    }
  }

}
