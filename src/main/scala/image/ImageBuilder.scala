package image

import image.pixel.Pixel
import scala.Vector
import image.Image

class ImageBuilder[T <: Pixel] private (width : Int, height : Int, private val data : Vector[Vector[T]]) {
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

  def set(i : Int, j : Int, value : T): Option[ImageBuilder[T]] = {
    if (!valid_idxs(i, j)) {
      return None;
    }

    return Some(new ImageBuilder[T](width, height, data.updated(i, data(i).updated(j, value))));
  }

  def collect(): Image[T] = {
    return Image(data);
  }
}

object ImageBuilder {
  def apply[T <: Pixel](width : Int, height : Int, fill : T): Option[ImageBuilder[T]] = {
    if (width <= 0 || height <= 0) {
      return None;
    }
    val data : Vector[Vector[T]] = Vector.fill[T](width, height)(fill);
    return Some(new ImageBuilder[T](width, height, data));
  }

  def apply[T <: Pixel](img : Image[T]): ImageBuilder[T] = {
    val width = img.data.length;
    val height = img.data(0).length;
    return new ImageBuilder[T](width, height, img.data);
  }
}
