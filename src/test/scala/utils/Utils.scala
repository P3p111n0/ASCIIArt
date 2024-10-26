package utils;

import image.pixel.RGBPixel;
import scala.util.Random

object TestUtils {
  private val gen = new Random;
  def get_random_rgb_pixel(): RGBPixel = {
    val r = gen.between(0, 256);
    val g = gen.between(0, 256);
    val b = gen.between(0, 256);
    
    RGBPixel(r, g, b) match {
      case Right(_) => assert(false); // this shouldnt happen
      case Left(p) => p;
    }
  }

  def get_random_int(): Int = {
    return gen.nextInt();
  }
}
