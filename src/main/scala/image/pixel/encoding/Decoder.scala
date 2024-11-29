package image.pixel.encoding

import image.pixel.Pixel;

/**
 * Decoder is a trait that represents a decoder from a type T to a Pixel type.
 *
 * @tparam T Source type
 * @tparam U Target pixel type
 */
trait Decoder[T, U <: Pixel] {
  def apply(elem: T): U;
}

/**
 * FromIntDecoder is a trait that represents a decoder from an Int to a Pixel type.
 *
 * @tparam U Target pixel type
 */
trait FromIntDecoder[U <: Pixel] extends Decoder[Int, U] {
  override def apply(elem: Int): U;
}
