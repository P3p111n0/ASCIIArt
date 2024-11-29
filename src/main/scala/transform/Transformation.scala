package transform;

import image.pixel.Pixel
import image.Image

/**
 * A trait that represents a transformation of an image.
 *
 * @tparam T The input pixel type.
 * @tparam U The output pixel type.
 */
trait Transformation[T <: Pixel, U <: Pixel] {
  /**
   * Applies the transformation to an image.
   *
   * @param img The image to transform.
   * @return The transformed image.
   */
  def apply(img: Image[T]): Image[U];
}
