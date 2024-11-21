package transform.scaling;

import image.pixel.Pixel
import image.Image
import image.ImageBuilder

class QuadrupleScaler[T <: Pixel] extends Scaler[T] {
  override def apply(img: Image[T]): Image[T] = {
    var builder = ImageBuilder(img.width() * 2, img.height() * 2, img.iterate().next().value) match {
      case Left(value) => value;
      case _ => assert(false);
    }

    for (pos <- img.iterate()) {
      for (i <- 0 until 2) {
        builder = builder.set(2 * pos.row + i, 2 * pos.col, pos.value) match {
          case Left(b) => b;
          case _ => assert(false);
        }
        builder = builder.set(2 * pos.row + i, 2 * pos.col + 1, pos.value) match {
          case Left(b) => b;
          case _ => assert(false);
        }
      }
    }

    return builder.collect();
  }
}
