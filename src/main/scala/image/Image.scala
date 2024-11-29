package image;

import image.data.ImageDataContainer;
import image.pixel.Pixel;
import iterator.ImageIterator;


/**
 * Image is an immutable image object.
 *
 * @param data Image data container
 * @tparam T Pixel type
 */
class Image[T <: Pixel](val data: ImageDataContainer[T]) {
  def width() = data.width();

  def height() = data.height();

  def iterate(): ImageIterator[T] = {
    return ImageIterator(this);
  }
}