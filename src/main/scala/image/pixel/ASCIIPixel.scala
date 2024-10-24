package image.pixel

case class ASCIIPixel private (c : Char) extends Pixel {}

object ASCIIPixel {
  private val default = new ASCIIPixel(0);

  def apply(c : Char): Option[ASCIIPixel] = {
    return Some(new ASCIIPixel(c));
  }

  def apply(): ASCIIPixel = {
    return default;
  }
}
