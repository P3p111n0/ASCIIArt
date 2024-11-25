package image;

import image.data.ImageDataContainer;
import image.pixel.Pixel;
import iterator.ImageIterator;

class Image[T <: Pixel](private[image] val data : ImageDataContainer[T]) {
  def width() = data.width();
  def height() = data.height();

  def iterate(): ImageIterator[T] = {
    return ImageIterator(this);
  }
}
