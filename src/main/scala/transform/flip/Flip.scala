package transform.flip;

import image.pixel.Pixel
import transform.Transformation
import image.Image

trait Flip[T <: Pixel] extends Transformation[T, T] {
  override def apply(img: Image[T]): Image[T]; 
}
