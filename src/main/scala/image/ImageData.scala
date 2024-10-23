package image

import scala.Array
import pixel.Pixel
import scala.util.boundary, boundary.break

class ImageData[T <: Pixel] private (private val data : Array[Array[T]]) {
  private val width = data.length;
  private val height = data(0).length;

  def get_width(): Int = {
    width;
  }

  def get_height(): Int = {
    height;
  }

  def get_width_iter(): Int = {
    width - 1;
  }

  def get_height_iter(): Int = {
    height - 1;
  }

  def at(row : Int, col : Int): Option[T] = {
    val validate = (x : Int, limit : Int) => x >= 0 && x < limit;
    if (!validate(row, width) || !validate(col, height)) {
      return None;
    }
    return Some(data(row)(col));
  }
}

object ImageData {
  def apply[T <: Pixel](data : Array[Array[T]]): Option[ImageData[T]] = {
    if (data.isEmpty) {
      return None;
    }

    val height = data(0).length;
    if (height == 0) {
      return None;
    }
    var fail = false;
    boundary:
      for (vec <- data) {
        if (vec.length != height) {
          fail = true;
          break();
        }
    }
    if (fail) {
      return None;
    }

    Some(new ImageData[T](data));
  }
}
