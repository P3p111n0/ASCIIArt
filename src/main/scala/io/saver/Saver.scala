package io.saver

import error.Error
import image.Image
import image.pixel.Pixel

/**
 * A trait that defines the interface for an image saver.
 *
 * @tparam T Source pixel type.
 */
trait Saver[T <: Pixel] {
  /**
   * Save an image to a file.
   *
   * @param img The image to save.
   * @return An error if the image could not be saved properly.
   */
  def save(img: Image[T]): Option[Error];
}
