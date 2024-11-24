package image.data;

import scala.Vector
import image.pixel.Pixel
import scala.util.boundary, boundary.break
import error.Error

class ImageData[T <: Pixel] private[image] (private val data : Vector[Vector[T]]) extends ImageDataContainer[T] {
  private def is_inbounds(i : Int, j : Int): Boolean = {
    if (i < 0 || i >= width()) {
      return false;
    } else if (j < 0 || j >= height()) {
      return false;
    }
    return true;
  }

  private def get_inbounds(i : Int, j : Int): T = {
    return data(i)(j);
  }

  private def set_inbounds(i : Int, j : Int, value : T): ImageData[T] = {
    val new_data = data.updated(i, data(i).updated(j, value));
    return new ImageData(new_data);
  }

  def width(): Int = {
    return data.length;
  }

  def height(): Int = {
    return data(0).length;
  }

  def at(row : Int, col : Int): Option[T] = {
    if (!is_inbounds(row, col)) {
      return None;
    }
    return Some(data(row)(col));
  }

  def set(row : Int, col : Int, value : T): Option[ImageData[T]] = {
    if (!is_inbounds(row, col)) {
      return None;
    }
    val new_data = data.updated(row, data(row).updated(col, value));
    return Some(new ImageData(new_data));
  }

  def map[U <: Pixel](fn : T => U): ImageData[U] = {
    val vec: Vector[Vector[U]] = data.map(_.map(fn));
    return new ImageData[U](vec);
  }
}

object ImageData {
  def apply[T <: Pixel](data : Vector[Vector[T]]): Either[ImageData[T], Error] = {
    if (data.isEmpty) {
      return Right(new Error("ImageData: Data vector is empty."));
    }

    val height = data(0).length;
    if (height == 0) {
      return Right(new Error("ImageData: Found column of height 0."));
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
      return Right(new Error("ImageData: Column height mismatch found."));
    }

    return Left(new ImageData[T](data));
  }
}
