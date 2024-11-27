package image.pixel.generator;

import image.pixel.RGBPixel;
import scala.util.Random;

class RGBGenerator(private val seed : Int) extends PseudoRandomGen[RGBPixel](seed) {
  override def next(): RGBPixel = {
    val r = gen.between(0, 256);
    val g = gen.between(0, 256);
    val b = gen.between(0, 256);
    return new RGBPixel(r, g, b);
  }
}
