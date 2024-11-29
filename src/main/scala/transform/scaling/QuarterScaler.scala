package transform.scaling;

import image.pixel.Pixel
import image.Image
import image.iterator.ImageElement
import image.ImageBuilder
import error.InternalException

class QuarterScaler[T <: Pixel] extends Scaler[T] {
  override def apply(img: Image[T]): Image[T] = {
    val new_rows = img.width() / 2 + (img.width() % 2);
    val new_cols = img.height() / 2 + (img.height() % 2);
    var builder = ImageBuilder(new_rows, new_cols, img.iterate().next().value) match {
      case Left(b) => b;
      case _ => throw new InternalException("QuarterScaler: Failed to construct builder.");
    }

    var row_skip = 0;
    var col_skip = 0;
    for (pos <- img.iterate()) {
      row_skip = pos.row % 2;
      if (row_skip == 1) {
        col_skip = 0;
      } else {
        if (col_skip % 2 == 0) {
          builder = builder.set(pos.row / 2, pos.col / 2, pos.value) match {
            case Left(value) => value;
            case _ => throw new InternalException("QuarterScaler: Failed to set at (%d, %d).".format(pos.row / 2, pos.col / 2));
          }
          if (pos.row % 2 != 0) {
            throw new InternalException("QuarterScaler: Row %d is not even.".format(pos.row));
          } else if (pos.col % 2 != 0) {
            throw new InternalException("QuarterScaler: Column %d is not even.".format(pos.col));
          }
        }
        col_skip += 1;
        col_skip %= 2;
      }
    }

    return builder.collect();
  }
}
