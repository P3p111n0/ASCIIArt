package transform.flip;

import image.pixel.Pixel
import image.Image
import image.ImageBuilder
import scala.util.boundary, boundary.break
import error.InternalException

class XFlip[T <: Pixel] extends Flip[T] {
  override def apply(img: Image[T]): Image[T] = {
    var builder = ImageBuilder(img);

    boundary {
      for (pos <- img.iterate()) {
        if (pos.row == (img.width() / 2 + (img.width() % 2)) && pos.col == 0) {
          break();
        }

        val new_row = img.width() - 1 - pos.row;
        builder = builder.swap(pos.row, pos.col, new_row, pos.col) match {
          case Left(value) => value;
          case _ => throw new InternalException("XFlip: Failed to swap (%d, %d) with (%d, %d).".format(pos.row, pos.col, new_row, pos.col)); 
        }
      }
    }

    return builder.collect();
  }
}
