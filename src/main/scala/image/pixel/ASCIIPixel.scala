package image.pixel

import scala.Either
import error.Error

case class ASCIIPixel private[pixel](c: Char) extends Pixel {}

object ASCIIPixel {
  private val default = new ASCIIPixel(0);

  def apply(c: Char): Either[ASCIIPixel, Error] = {
    if (c < 0x20 || c > 0x7e) {
      return Right(new Error("ASCIIPixel: Character 0x%s is not printable".format(c.toInt.toHexString)));
    }
    return Left(new ASCIIPixel(c));
  }

  def apply(): ASCIIPixel = {
    return default;
  }
}
