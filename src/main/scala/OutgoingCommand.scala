import endless.core.protocol.Decoder

trait OutgoingCommand[+R] {
  def payload: Array[Byte]
  def replyDecoder: Decoder[R]
}
