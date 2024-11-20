package image;

import image.ImageData;
import image.pixel.Pixel

class Image[T <: Pixel] private[image](private[image] val data : ImageData[T]) {
  def width() = data.get_width();
  def height() = data.get_height();
}
