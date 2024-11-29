package loader;

import image.Image;
import image.pixel.{RGBPixel, Pixel};
import error.Error;

/**
 * A trait that defines the interface for an image loader.
 *
 * @tparam T The type of pixel that the loader will load.
 */
trait Loader[T <: Pixel] {
  /**
   * Load an image from a file.
   *
   * @return A loaded image or an error if the image could not be loaded properly.
   */
  def load(): Either[Image[T], Error];
}
