package image.pixel.encoding

import image.pixel.Pixel;

trait Decoder[T, U <: Pixel] {
  def apply(elem : T): U;
}

trait FromIntDecoder[U <: Pixel] extends Decoder[Int, U] {
  override def apply(elem: Int): U;
}
