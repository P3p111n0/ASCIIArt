package transform;

import image.pixel.Pixel;
import image.pixel.RGBPixel

trait PixelTransform[T <: Pixel, U <: Pixel] {
  def apply(value : T): U;
}

trait RGBTransform[U <: Pixel] extends PixelTransform[RGBPixel, U] {
  override def apply(value : RGBPixel): U;
}
