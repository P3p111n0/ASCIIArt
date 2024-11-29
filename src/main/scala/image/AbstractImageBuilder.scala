package image

import image.pixel.Pixel
import error.Error

/**
 * AbstractImageBuilder is an interface for immutable image builder objects.
 *
 * @tparam T Pixel type
 */
trait AbstractImageBuilder[T <: Pixel] {
  /**
   * Get the width of the image.
   *
   * @return Width of the image
   */
  def get_width(): Int;

  /**
   * Get the height of the image.
   *
   * @return Height of the image
   */
  def get_height(): Int;

  /**
   * Get the pixel at the specified indices.
   *
   * @param i Row index
   * @param j Column index
   * @return Pixel at the specified indices or an error, if indices are out of bounds
   */
  def get(i: Int, j: Int): Either[T, Error];

  /**
   * Set the pixel at the specified indices.
   *
   * @param i     Row index
   * @param j     Column index
   * @param value Value to set
   * @return New image builder with the pixel set at the specified indices or an error, if indices are out of bounds.
   */
  def set(i: Int, j: Int, value: T): Either[AbstractImageBuilder[T], Error];

  /**
   * Creates an image from the current state of the builder.
   *
   * @return Generated image
   */
  def collect(): Image[T];

  /**
   * Apply a function to each pixel in the image.
   *
   * @param fn Function to apply
   * @tparam U Target pixel type
   * @return New image builder with the function applied to each pixel
   */
  def map[U <: Pixel](fn: T => U): AbstractImageBuilder[U];

  /**
   * Swap two pixels in the image.
   *
   * @param i  Row index of the first pixel
   * @param j  Column index of the first pixel
   * @param i_ Row index of the second pixel
   * @param j_ Column index of the second pixel
   * @return New image builder with the two pixels swapped or an error, if at least one of indices are out of bounds
   */
  def swap(i: Int, j: Int, i_ : Int, j_ : Int): Either[AbstractImageBuilder[T], Error];
}
