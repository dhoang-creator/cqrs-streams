import endless.\/
import endless.core.protocol.Decoder

class BookingCommandProtocol extends BookingCommandProtocol[BookingAlg] {
  override def client: BookingAlg[OutgoingCommand[*]] =
    new BookingAlg[OutgoingCommand[*]] {
      def place(booking: Booking): OutgoingCommand[AlreadyExists.type \/ Unit] =
        outgoingCommand[proto.BookingCommand, proto.PlaceCommandReply, AlreadyExists.type \/ Unit](
          command = proto.PlaceBooking(booking.transformInto[proto.Booking]),
          replyMapper = _.reply match {
            case proto.PlaceCommandReply.AlreadyExistsV1(_) => AlreadyExists.asLeft
            case proto.PlaceCommandReply.Unit(_) => ().asRight
          }
        )
    }

  override def server[F[_]]: Decoder[IncomingCommand[F, BookingAlg]] =
    ProtobufDecoder[proto.BookingCommand].map(_.command match {
      case proto.PlaceBookingV1(booking) =>
        incomingCommand[F, proto.PlaceCommandReply, AlreadyExists.type \/ Unit](
          run = _.place(booking.transformInto[Booking]),
          replyContramapper = {
            case Left(AlreadyExists) => proto.PlaceCommandReply.Reply.AlreadyExistsV1()
            case Right(()) => proto.PlaceCommandReply.Reply.Unit()
          }
        )
    })

}
