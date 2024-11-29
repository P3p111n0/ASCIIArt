package image.pixel.encoding

import image.pixel.Pixel;

trait Encoder[T <: Pixel, U] {
  def apply(elem: T): U; 
}

trait ToIntEncoder[T <: Pixel] extends Encoder[T, Int] {
  override def apply(elem: T): Int; 
}
