package image.iterator;

import image.Image;
import image.pixel.Pixel
import error.Error;

case class ImageElement[T <: Pixel] private[iterator](val row : Int, val col : Int, val value : T);

class ImageIterator[T <: Pixel] private[image](private val image : Image[T], private var row : Int, private var col : Int) extends Iterator[ImageElement[T]] {
  override def hasNext(): Boolean = {
    return !(row == image.height() && col == 0);
  }

  override def next(): ImageElement[T] = {
    if (!hasNext()) {
      throw new NoSuchElementException;
    } 

    val pixel = image.data.at(row, col) match {
      case Some(p) => p;
      case None => assert(false);
    }

    val result = new ImageElement(row, col, pixel);

    col += 1;
    if (col == image.width()) {
      col = 0;
      row += 1;
    }

    return result;
  }
}

object ImageIterator {
  private def validate(x : Int, limit : Int): Boolean = {
    return x >= 0 && x < limit;
  }

  def apply[T <: Pixel](img : Image[T]): ImageIterator[T] = {
    return new ImageIterator(img, 0, 0);
  }

  def apply[T <: Pixel](img : Image[T], row : Int, col : Int): Either[ImageIterator[T], Error] = {
    if (!validate(row, img.height())) {
      return Right(new Error("ImageIterator: Row index out of range."));
    }

    if (!validate(col, img.width())) {
      return Right(new Error("ImageIterator: Column index out of range."));
    }

    return Left(new ImageIterator(img, row, col));
  }
}
