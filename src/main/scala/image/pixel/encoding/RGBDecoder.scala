package image.pixel.encoding;

import image.pixel.RGBPixel;

object RGBDecoder extends FromIntDecoder[RGBPixel] {
  override def apply(elem: Int): RGBPixel = {
    val r = (elem & 0xff0000) >> 16;
    val g = (elem & 0xff00) >> 8;
    val b = elem & 0xff;

    RGBPixel(r, g, b) match {
      case Left(p) => p;
      case _ => assert(false); // this shouldn't happen
    }
  }
}
