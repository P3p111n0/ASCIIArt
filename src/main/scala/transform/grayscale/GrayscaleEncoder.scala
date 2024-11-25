package transform.grayscale

import image.pixel.RGBPixel
import image.pixel.encoding.Encoder

object GrayscaleEncoder extends Encoder[RGBPixel, Int] {
  override def apply(elem: RGBPixel): Int = {
    return ((0.3 * elem.r) + (0.59 * elem.g) + (0.11 * elem.b)).toInt;
  }
}