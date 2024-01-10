import cats.Monad

case class BookingRepository[F[_]: Monad](
                                         repository: Repository[F, BookingID, BookingAlg]
                                         ) extends BookingRepositoryAlg[F] {
  def bookingFor(bookingID: BookingID): BookingAlg[F] = repository.entityFor(bookingID)
}
