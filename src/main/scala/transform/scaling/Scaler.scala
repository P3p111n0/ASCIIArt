package transform.scaling

import image.pixel.Pixel
import transform.Transformation
import image.Image

/**
 * A trait for scaling an image by a certain factor
 *
 * @tparam T Source and target pixel type.
 */
trait Scaler[T <: Pixel] extends Transformation[T, T] {
  /**
   * Scales the image by a certain factor.
   *
   * @param img The image to scale.
   * @return The scaled image.
   */
  override def apply(img: Image[T]): Image[T];
}
