import endless.\/

import java.time.Instant

// With the article example, we're creating a distributed system for booking rides
final case class LatLon(lat: Double, lon: Double)
final case class Booking(
  time: Instant,
  passengerCount: Int,
  origin: LatLon,
  destination: LatLon
                        )

// Below is a Tagless Algebra for the Booking System
trait BookingAlg[F[_]] {
  // 'place' creates an entity
  def place(booking: Booking): F[AlreadyExists.type \/ Unit]
  // 'getBooking' returns its definition if the entity already exists
  def getBooking: F[Unknown.type \/ Booking]

  def setRoute(steps: List[LatLon]): F[Unknown.type \/ Unit]
  def getRoute: F[Unknown.type \/ List[LatLon]]
}
trait BookingRepositoryAlg[F[_]] {
  def bookingFor(bookingID: BookingID): BookingAlg[F]
}

// Note that: type \/[A, B] = Either[A, B]