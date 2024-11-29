package transform.grayscale

import image.pixel.Pixel
import image.pixel.encoding.Encoder

case class GrayscaleInverter[T <: Pixel](val to_grayscale : Encoder[T, Int]) extends Encoder[T, Int] {
  private val white : Int = 255;
  override def apply(elem: T): Int = {
    return white - to_grayscale(elem);
  }
}