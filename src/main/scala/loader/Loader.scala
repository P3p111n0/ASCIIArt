package loader;

import image.Image;
import image.pixel.{RGBPixel, Pixel};
import error.Error;

trait Loader[T <: Pixel] {
  def load(): Either[Image[T], Error];
}
