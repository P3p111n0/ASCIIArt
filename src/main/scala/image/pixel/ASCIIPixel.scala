package image.pixel

import scala.Either
import error.Error

case class ASCIIPixel private[pixel] (c : Char) extends Pixel {}

object ASCIIPixel {
  private val default = new ASCIIPixel(0);

  def apply(c : Char): Either[ASCIIPixel, Error] = {
    return Left(new ASCIIPixel(c));
  }

  def apply(): ASCIIPixel = {
    return default;
  }
}
