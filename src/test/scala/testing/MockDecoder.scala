package testing

import image.pixel.RGBPixel
import image.pixel.encoding.Decoder

object MockDecoder extends Decoder[Int, RGBPixel] {
  override def apply(elem: Int): RGBPixel = {
    val r = (elem & 0xff0000) >> 16;
    val g = (elem & 0xff00) >> 8;
    val b = elem & 0xff;

    RGBPixel(r, g, b) match {
      case Left(p) => p;
      case _ => assert(false);
    }
  }
}
