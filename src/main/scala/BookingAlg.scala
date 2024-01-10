import endless.\/

trait BookingAlg[F[_]] {
  def place(booking: Booking): F[AlreadyExists.type \/ Unit]
  def getBooking: F[Unknown.type \/ Booking]

  def setRoute(steps: List[LatLon]): F[Unknown.type \/ Unit]
  def getRoute: F[Unknown.type \/ List[LatLon]]
}
trait BookingRepositoryAlg[F[_]] {
  def bookingFor(bookingID: BookingID): BookingAlg[F]
}
