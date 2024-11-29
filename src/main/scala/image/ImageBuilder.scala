package image

import error.Error
import image.pixel.Pixel
import scala.Vector
import image.Image
import image.data.ImageDataContainer
import image.data.ImageData

class ImageBuilder[T <: Pixel] private(private val data: ImageDataContainer[T]) extends AbstractImageBuilder[T] {
  private def valid_idxs(i: Int, j: Int): Boolean = {
    val validate_idx = (x: Int, limit: Int) => 0 <= x && x < limit;
    return validate_idx(i, data.width()) && validate_idx(j, data.height());
  }

  def get_width(): Int = data.width();

  def get_height(): Int = data.height();

  def get(i: Int, j: Int): Either[T, Error] = {
    data.at(i, j) match {
      case None => Right(new Error("ImageBuilder::get: Pixel indices out of bounds."));
      case Some(x) => Left(x);
    }
  }

  def set(i: Int, j: Int, value: T): Either[ImageBuilder[T], Error] = {
    data.set(i, j, value) match {
      case None => return Right(new Error("ImageBuilder::set: indices (%d, %d) out of range".format(i, j)));
      case Some(new_data) => return Left(new ImageBuilder[T](new_data));
    }
  }

  def collect(): Image[T] = {
    return Image(data);
  }

  def map[U <: Pixel](fn: T => U): ImageBuilder[U] = {
    return new ImageBuilder[U](data.map(fn));
  }

  def swap(i: Int, j: Int, i_ : Int, j_ : Int): Either[ImageBuilder[T], Error] = {
    if (!valid_idxs(i, j)) {
      return Right(new Error("ImageBuilder::swap: First pixel indices out of bounds."));
    }
    if (!valid_idxs(i_, j_)) {
      return Right(new Error("ImageBuilder::swap: Second pixel indices out of bounds."));
    }

    val first = data.at(i, j).get;
    val second = data.at(i_, j_).get;

    val new_data = data.set(i, j, second).get.set(i_, j_, first).get;
    return Left(new ImageBuilder[T](new_data));
  }
}

object ImageBuilder {
  def apply[T <: Pixel](width: Int, height: Int, fill: T): Either[ImageBuilder[T], Error] = {
    val err_val_msg = "ImageBuilder: %s must be positive";
    if (width <= 0) {
      return Right(new Error(err_val_msg.format("Width")));
    }
    if (height <= 0) {
      return Right(new Error(err_val_msg.format("Height")));
    }

    val data_vec: Vector[Vector[T]] = Vector.fill[T](width, height)(fill);
    ImageData[T](data_vec) match {
      case Right(e) => return Right(e);
      case Left(img) => return Left(new ImageBuilder[T](img));
    }
  }

  def apply[T <: Pixel](img: Image[T]): ImageBuilder[T] = {
    return new ImageBuilder[T](img.data);
  }
}
