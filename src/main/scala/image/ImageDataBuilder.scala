package image

import image.pixel.Pixel
import scala.Vector

class ImageDataBuilder[T <: Pixel] private (width : Int, height : Int, private val data : Vector[Vector[T]]) {
  private def valid_idxs(i : Int, j : Int): Boolean = {
    val validate_idx = (x : Int, limit : Int) => 0 <= x && x < limit;
    return validate_idx(i, width) && validate_idx(j, height);
  }

  def get_width_iter(): Int = width;

  def get_height_iter(): Int = height;

  def get(i : Int, j : Int): Option[T] = {
    if (!valid_idxs(i, j)) {
      return None;
    }
    return Some(data(i)(j));
  }

  def set(i : Int, j : Int, value : T): Option[ImageDataBuilder[T]] = {
    if (!valid_idxs(i, j)) {
      return None;
    }

    return Some(new ImageDataBuilder[T](width, height, data.updated(i, data(i).updated(j, value))));
  }

  def collect(): Option[ImageData[T]] = {
    return ImageData(data);
  }
}
