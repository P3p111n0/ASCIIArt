package transform.scaling

import transform.scaling.Scaler
import image.pixel.Pixel
import image.Image

class OriginalScaler[T <: Pixel] extends Scaler[T] {
  override def apply(img: Image[T]): Image[T] = {
    return img;
  }
}
