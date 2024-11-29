package image.data;

import image.pixel.Pixel
import error.Error

/**
 * An immutable container interface for image data.
 *
 * @tparam T The type of pixel stored in the container.
 */
trait ImageDataContainer[T <: Pixel] {
  /**
   * The width of the image.
   *
   * @return The width of the image.
   */
  def width(): Int;

  /**
   * The height of the image.
   *
   * @return The height of the image.
   */
  def height(): Int;

  /**
   * Get the pixel at the given indices.
   *
   * @param i The row index.
   * @param j The column index.
   * @return The pixel at the given indices, or None if indices are out of bounds.
   */
  def at(i: Int, j: Int): Option[T];

  /**
   * Set the pixel at the given indices.
   *
   * @param i     The row index.
   * @param j     The column index.
   * @param value The pixel to set.
   * @return The new image data container with the pixel set, or None if indices are out of bounds.
   */
  def set(i: Int, j: Int, value: T): Option[ImageDataContainer[T]];

  /**
   * Map a function over the pixels in the image.
   *
   * @param fn The function to map over the pixels.
   * @tparam U Target pixel type.
   * @return The new image data container with the function mapped over the pixels.
   */
  def map[U <: Pixel](fn: T => U): ImageDataContainer[U];
}
