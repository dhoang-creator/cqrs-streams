// Through the usage of Event Folding within an Application leads to simply a tupled function of a possibly defined state and the event
// This should lead to either a newer version of the state or an error
import endless.\/

class BookingEventApplier extends EventApplier[BookingState, BookingEvent] {
  def apply(state: Option[BookingState], event: BookingEvent): String \/ Option[BookingState] =
    (event match {
      case BookingPlaced(booking) =>
        state
          .toLeft(BookingState(definition = booking, route = Nil))
          .leftMap(_ => "Booking already exists")
      case RouteSet(steps: List[LatLon]) =>
        state
          .toRight("Attempt to set route on unknown booking")
          .map(_.copy(route = steps))
    }).map(Option(_))

}
