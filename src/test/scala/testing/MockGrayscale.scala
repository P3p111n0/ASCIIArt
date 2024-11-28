package testing

import image.pixel.RGBPixel
import image.pixel.encoding.Encoder;

object MockGrayscale extends Encoder[RGBPixel, Int] {
  override def apply(elem: RGBPixel): Int = {
    elem match {
      case RGBPixel(0, 0, 0) => 0;
      case RGBPixel(255, 255, 255) => 255;
      case RGBPixel(127, 85, 13) => 89;
      case _ => assert(false);
    }
  }
}