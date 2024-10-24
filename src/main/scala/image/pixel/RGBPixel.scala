package image.pixel

import scala.Option

case class RGBPixel private (r : Int, g : Int, b : Int) extends Pixel {
  object RGBPixelDefault extends RGBPixel(0, 0, 0);
  override def default(): RGBPixel = {
    return new RGBPixelDefault;
  }
}

object RGBPixel {
  def apply(r : Int, g : Int, b : Int): Option[RGBPixel] = {
    val in_range = (x : Int) => 0 <= x && x <= 255;
    if (!in_range(r) || !in_range(g) || !in_range(b)) {
      return None;
    }
    return Some(new RGBPixel(r, g, b));
  }
}
