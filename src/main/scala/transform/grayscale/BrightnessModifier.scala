package transform.grayscale

import image.pixel.Pixel
import image.pixel.encoding.Encoder

case class BrightnessModifier[T <: Pixel](val offset : Int, val to_grayscale : Encoder[T, Int]) extends Encoder[T, Int] {
  private val minimum = 0;
  private val maximum = 255;
  override def apply(elem: T): Int = {
    val converted_value = to_grayscale(elem);
    val result = Math.max(minimum, Math.min(converted_value + offset, maximum));
    return result;
  }
}