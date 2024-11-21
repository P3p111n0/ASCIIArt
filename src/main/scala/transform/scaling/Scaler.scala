package transform.scaling

import image.pixel.Pixel
import transform.Transformation
import image.Image

trait Scaler[T <: Pixel] extends Transformation[T, T] {
  override def apply(img: Image[T]): Image[T];
}
