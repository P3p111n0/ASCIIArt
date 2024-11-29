package image.pixel.generator;

import image.pixel.Pixel;
import scala.util.Random;
import java.{util => ju}

/**
 * Generator is a trait that represents a generator of pixels.
 *
 * @tparam T Pixel type
 */
trait Generator[T <: Pixel] {
  /**
   * Returns the next pixel.
   *
   * @return the next pixel
   */
  def next(): T;
}

/**
 * PseudoRandomGen is a trait that represents a pseudo-random generator of pixels.
 *
 * @tparam T Pixel type
 * @param seed generator seed
 */
trait PseudoRandomGen[T <: Pixel](private val seed: Int) extends Generator[T] {
  protected val gen = new Random(seed);
}
