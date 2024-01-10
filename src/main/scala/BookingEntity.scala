import cats.Monad
import endless.core.entity.Entity
import org.typelevel.log4cats.Logger

/*
  Like Cats MTL, Ask and Tell have similar reader-write abilities are exposed via the Entity typeclass
    trait Entity[F[_], S, E] extends StateReader[F, S] with EventWriter[F, E] with Monad[F]
      - StateReader allows for read: F[S]
      - EventWriter provides the ability to persist events: write(events: E*): F[Unit]
 */

// Note the below syntax of self containing a high kinded type with multiple other types which is usually found within implicit defs -> explore the structure here
final case class BookingEntity[F[_] : Monad : Logger](
                                                     entity: Entity[F, BookingState, BookingEvent]
                                                     ) extends BookingAlg[F] {

  def place(booking: Booking): F[AlreadyExists.type \/ Unit] =
    entity.ifUnknownF(entity.write(BookingPlaced(booking)))(_ => AlreadyExists)

  def getBooking: F[Unknown.type \/ Booking] =
    entity.ifKnown(_.definition)(Unknown)

  def setRoute(steps: List[LatLon]): F[Unknown.type \/ Unit] =
    entity.ifKnownF(entity.write(RouteSet(steps)))(Unknown)

  def getRoute: F[Unknown.type \/ List[LatLon]] =
    entity.ifKnown(_.route)(Unknown)
}
