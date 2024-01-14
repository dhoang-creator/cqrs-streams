import cats.~>

trait CommandRouter[F[_], ID] {
  def routeForID(id: ID): OutgoingCommand[*] ~> F
}
