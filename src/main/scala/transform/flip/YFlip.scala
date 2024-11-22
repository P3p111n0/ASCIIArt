package transform.flip;

import image.pixel.Pixel
import image.Image
import image.ImageBuilder
import scala.util.boundary, boundary.break

class YFlip[T <: Pixel] extends Flip[T] {
  override def apply(img: Image[T]): Image[T] = {
    var builder = ImageBuilder(img);

      for (pos <- img.iterate()) {
        if (pos.col >= (img.height() / 2 + 1)) {
          // nop
        } else {
          val new_col = img.height() - 1 - pos.col;
          builder = builder.swap(pos.row, pos.col, pos.row, new_col) match {
            case Left(value) => value;
            case _ => assert(false);
          }
        }
   }

    return builder.collect();
  }
}
