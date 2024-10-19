package image.pixel

import scala.Option
import scala.Range

case class RGBPixel private (r : Int, g : Int, b : Int) extends Pixel {}

object RGBPixel {
  def apply(r : Int, g : Int, b : Int): Option[RGBPixel] = {
    val in_range = (x : Int) => 0 <= x && x <= 255;
    if (!in_range(r) || !in_range(g) || !in_range(b)) {
      return None;
    }
    return RGBPixel(r, g, b);
  }
}
