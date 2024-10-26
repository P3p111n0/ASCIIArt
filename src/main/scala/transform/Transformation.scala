package transform;

import image.pixel.Pixel
import image.Image

trait Transformation[T <: Pixel, U <: Pixel] {
  def apply(img : Image[T]): Image[U];
}
