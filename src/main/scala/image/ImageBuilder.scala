package image

import error.Error
import image.pixel.Pixel
import scala.Vector
import image.Image

class ImageBuilder[T <: Pixel] private (width : Int, height : Int, private val data : Vector[Vector[T]]) {
  private def valid_idxs(i : Int, j : Int): Boolean = {
    val validate_idx = (x : Int, limit : Int) => 0 <= x && x < limit;
    return validate_idx(i, width) && validate_idx(j, height);
  }

  def get_width(): Int = width;

  def get_height(): Int = height;

  def get(i : Int, j : Int): Either[T, Error] = {
    if (!valid_idxs(i, j)) {
      return Right(new Error("ImageBuilder::get: indices (%d, %d) out of range".format(i, j)));
    }
    return Left(data(i)(j));
  }

  def set(i : Int, j : Int, value : T): Either[ImageBuilder[T], Error] = {
    if (!valid_idxs(i, j)) {
      return Right(new Error("ImageBuilder::set: indices (%d, %d) out of range".format(i, j)));
    }

    return Left(new ImageBuilder[T](width, height, data.updated(i, data(i).updated(j, value))));
  }

  def collect(): Image[T] = {
    return Image(data);
  }
}

object ImageBuilder {
  def apply[T <: Pixel](width : Int, height : Int, fill : T): Either[ImageBuilder[T], Error] = {
    val err_val_msg = "ImageBuilder: %s must be positive";
    if (width <= 0) {
      return Right(new Error(err_val_msg.format("Width")));
    }
    if (height <= 0) {
      return Right(new Error(err_val_msg.format("Height")));
    }

    val data : Vector[Vector[T]] = Vector.fill[T](width, height)(fill);
    return Left(new ImageBuilder[T](width, height, data));
  }

  def apply[T <: Pixel](img : Image[T]): ImageBuilder[T] = {
    val width = img.data.length;
    val height = img.data(0).length;
    return new ImageBuilder[T](width, height, img.data);
  }
}
