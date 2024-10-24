package image.pixel

case class ASCIIPixel (c : Char) extends Pixel {
  object ASCIIPixelDefault extends ASCIIPixel(0) {}
  override def default(): ASCIIPixel = {
    return new ASCIIPixelDefault();
  }
}

object ASCIIPixelDefault extends ASCIIPixel(0) {}
