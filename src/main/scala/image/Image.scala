package image;

import image.ImageData;
import image.pixel.Pixel;
import iterator.ImageIterator;

class Image[T <: Pixel] private[image](private[image] val data : ImageData[T]) {
  private val start_row = 0;
  private val start_col = 0;

  def width() = data.get_width();
  def height() = data.get_height();

  def iterate(): ImageIterator[T] = {
    return ImageIterator(this);
  }
}
