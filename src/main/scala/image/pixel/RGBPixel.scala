package image.pixel

import error.Error
import scala.Either

case class RGBPixel private (r : Int, g : Int, b : Int) extends Pixel {}

object RGBPixel extends Pixel {
  private val default = new RGBPixel(0, 0, 0);
  def apply(r : Int, g : Int, b : Int): Either[RGBPixel, Error] = {
    val in_range = (x : Int) => 0 <= x && x <= 255;
    val err_str = "RGBPixel: %s value out of range (expected 0-255, got %d)";
    
    if (!in_range(r)) {
      return Right(new Error(err_str.format("Red", r)));
    } else if (!in_range(g)) {
      return Right(new Error(err_str.format("Green", g)));
    } else if (!in_range(b)) {
      return Right(new Error(err_str.format("Blue", b)));
    }

    return Left(new RGBPixel(r, g, b));
  }

  
  def apply(): RGBPixel = {
    return default; 
  }
}
