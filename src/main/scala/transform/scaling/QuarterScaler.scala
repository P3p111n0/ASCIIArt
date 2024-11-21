package transform.scaling;

import image.pixel.Pixel
import image.Image
import image.iterator.ImageElement
import image.ImageBuilder

class QuarterScaler[T <: Pixel] extends Scaler[T] {
  private def remainder_to_ceil(x : Int, y : Int): Int = {
    if (x % y == 0) {
      return 0;
    }
    return 1;
  }

  override def apply(img: Image[T]): Image[T] = {
    val new_rows = img.width() / 2 + remainder_to_ceil(img.width(), 2); 
    val new_cols = img.height() / 2 + remainder_to_ceil(img.height(), 2);
    var builder = ImageBuilder(new_rows, new_cols, img.iterate().next().value) match {
      case Left(b) => b;
      case _ => assert(false);
    }

    var row_skip = 0;
    var col_skip = 0;
    for (pos <- img.iterate()) {
      if (row_skip == 1) {
        // do nothing
      } else {
        if (col_skip % 2 == 0) {
          builder = builder.set(pos.row / 2, pos.col / 2, pos.value) match {
            case Left(value) => value;
            case _ => assert(false);
          }
          assert(pos.row % 2 == 0);
          assert(pos.col % 2 == 0);
        }
        col_skip += 1;
        col_skip %= 2;
      }
      
      row_skip = pos.row % 2;
    }
    
    return builder.collect();
  }
}
