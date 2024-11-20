package io.encoding;

import image.pixel.RGBPixel;

object RGBEncoder extends ToIntEncoder[RGBPixel] {
  override def apply(elem: RGBPixel): Int = {
    var res = 0;
    res |= (elem.r << 16);
    res |= (elem.g << 8);
    res |= elem.b;
    return res;
  }
}

object GrayscaleEncoder extends Encoder[RGBPixel, Double] {
  override def apply(elem: RGBPixel): Double = {
    return ((0.3 * elem.r) + (0.59 * elem.g) + (0.11 * elem.b));
  } 
}
