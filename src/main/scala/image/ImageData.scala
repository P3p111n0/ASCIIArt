package image

import scala.Vector
import pixel.Pixel
import scala.util.boundary, boundary.break

class ImageData[T <: Pixel] private (private val data : Vector[Vector[T]]) {
  val width: Int = data.length;
  val height: Int = data(0).length;

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
  def apply[T <: Pixel](data : Vector[Vector[T]]): Option[ImageData[T]] = {
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
