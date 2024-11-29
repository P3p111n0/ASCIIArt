package transform.flip;

import image.pixel.Pixel
import transform.Transformation
import image.Image

/**
 * Flip is a transformation that flips an image.
 *
 * @tparam T Source and target pixel type
 */
trait Flip[T <: Pixel] extends Transformation[T, T] {
  /**
   * Applies the flip transformation to an image.
   *
   * @param img Image to be flipped
   * @return Flipped image
   */
  override def apply(img: Image[T]): Image[T];
}
