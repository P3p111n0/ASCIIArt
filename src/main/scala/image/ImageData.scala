package image

import scala.Vector
import pixel.Pixel
import scala.util.boundary, boundary.break
import error.Error

class ImageData[T <: Pixel] private[image] (private val data : Vector[Vector[T]]) {
  val width: Int = data.length;
  val height: Int = data(0).length;
  private val validate = (x : Int, limit : Int) => x >= 0 && x < limit;

  private def is_inbounds(i : Int, j : Int): Boolean = {
    return validate(i, width) && validate(j, height);
  }

  private def get_inbounds(i : Int, j : Int): T = {
    return data(i)(j);
  }

  private def set_inbounds(i : Int, j : Int, value : T): ImageData[T] = {
    val new_data = data.updated(i, data(i).updated(j, value));
    return new ImageData(new_data);
  }

  def get_width(): Int = {
    return width;
  }

  def get_height(): Int = {
    return height;
  }

  def at(row : Int, col : Int): Option[T] = {
    if (!validate(row, width) || !validate(col, height)) {
      return None;
    }
    return Some(data(row)(col));
  }

  def set(row : Int, col : Int, value : T): Option[ImageData[T]] = {
    if (!validate(row, width) || !validate(col, height)) {
      return None;
    }
    val new_data = data.updated(row, data(row).updated(col, value));
    return Some(new ImageData(new_data));
  }

  def map[U <: Pixel](fn : T => U): ImageData[U] = {
    val vec: Vector[Vector[U]] = data.map(_.map(fn));
    return new ImageData[U](vec);
  }

  def swap(i : Int, j : Int, i_ : Int, j_ : Int): Either[ImageData[T], Error] = {
    if (!is_inbounds(i, j)) {
      return Right(new Error("ImageData::swap: First pair of indices (%d. %d) is out of range.".format(i, j)));
    } else if (!is_inbounds(i_, j_)) {
      return Right(new Error("ImageData::swap: Second pair of indices (%d. %d) is out of range.".format(i_, j_)));
    }

    var result = new ImageData(data); 
    val first = result.get_inbounds(i, j);
    val second = result.get_inbounds(i_, j_);

    result = result.set_inbounds(i, j, second);
    result = result.set_inbounds(i_, j_, first);

    return Left(result);
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
