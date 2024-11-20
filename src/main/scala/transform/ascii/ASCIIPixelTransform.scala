package transform.ascii;

import image.pixel.{ASCIIPixel, RGBPixel};
import transform.RGBTransform;
import io.encoding.Encoder

class ToASCIITransform[U](private val encoder : Encoder[RGBPixel, U],private val map : ASCIIMap[U]) extends RGBTransform[ASCIIPixel] {
  override def apply(value: RGBPixel): ASCIIPixel = {
    return map(encoder(value));
  }
}
