package transform.rotation

import image.Image
import image.pixel.Pixel
import transform.Transformation

/**
 * A trait that represents a rotation transformation.
 *
 * @tparam T Source and target pixel type.
 */
trait AbstractRotation[T <: Pixel] extends Transformation[T, T] {
  /**
   * Applies the transformation to an image.
   *
   * @param img The image to transform.
   * @return The transformed image.
   */
  override def apply(img: Image[T]): Image[T];
}
