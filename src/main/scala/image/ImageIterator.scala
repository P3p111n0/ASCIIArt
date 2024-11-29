package image.iterator;

import image.Image;
import image.pixel.Pixel
import error.Error;
import error.InternalException

// ImageElement represents a pixel of an image. Value is the pixel at coordinates (row, col).
case class ImageElement[T <: Pixel] private[iterator](val row: Int, val col: Int, val value: T);

/**
 * ImageIterator is an iterator for an image. It iterates over the pixels of an image in row-major order.
 *
 * @param image The image to iterate over.
 * @param row   Starting row index. (0-indexed)
 * @param col   Starting column index. (0-indexed)
 * @tparam T Pixel type
 */
class ImageIterator[T <: Pixel] private[image](private val image: Image[T], private var row: Int, private var col: Int) extends Iterator[ImageElement[T]] {
  override def hasNext(): Boolean = {
    return !(row == image.width() && col == 0);
  }

  override def next(): ImageElement[T] = {
    if (!hasNext()) {
      throw new NoSuchElementException;
    }

    val pixel = image.data.at(row, col) match {
      case Some(p) => p;
      case None => throw new InternalException("ImageIterator: Failed to get from data container.");
    }

    val result = new ImageElement(row, col, pixel);

    col += 1;
    if (col == image.height()) {
      col = 0;
      row += 1;
    }

    return result;
  }
}

object ImageIterator {
  private def validate(x: Int, limit: Int): Boolean = {
    return x >= 0 && x < limit;
  }

  def apply[T <: Pixel](img: Image[T]): ImageIterator[T] = {
    return new ImageIterator(img, 0, 0);
  }

  def apply[T <: Pixel](img: Image[T], row: Int, col: Int): Either[ImageIterator[T], Error] = {
    if (!validate(row, img.width())) {
      return Right(new Error("ImageIterator: Row index out of range."));
    }

    if (!validate(col, img.height())) {
      return Right(new Error("ImageIterator: Column index out of range."));
    }

    return Left(new ImageIterator(img, row, col));
  }
}
