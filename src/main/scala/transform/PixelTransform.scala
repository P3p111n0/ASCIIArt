package transform;

import image.pixel.Pixel;
import image.pixel.RGBPixel

/**
 * A trait that represents a transformation from one pixel type to another.
 *
 * @tparam T The input pixel type.
 * @tparam U The output pixel type.
 */
trait PixelTransform[T <: Pixel, U <: Pixel] {
  def apply(value: T): U;
}

/**
 * A trait that represents a transformation from a RGB pixel to another pixel type.
 *
 * @tparam U The output pixel type.
 */
trait RGBTransform[U <: Pixel] extends PixelTransform[RGBPixel, U] {
  override def apply(value: RGBPixel): U;
}
