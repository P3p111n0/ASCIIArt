package transform;

import image.Image
import error.Error
import image.ImageBuilder
import image.pixel.Pixel

class Rotation[T <: Pixel] private (var angle : Int) extends Transformation[T, T] {
  private def rotate_clockwise_90(img : Image[T]): Image[T] = {
    val og_builder = ImageBuilder(img);
    val fill : T = og_builder.get(0, 0) match {
      case Left(p) => p;
      case _ => assert(false) // this shouldn't happen
    }
    var builder = ImageBuilder[T](img.height(), img.width(), fill) match {
      case Left(b) => b;
      case _ => assert(false)
    }

    for (i <- 0 until og_builder.get_width()) {
      for (j <- 0 until og_builder.get_height()) {
        val pixel = og_builder.get(i, j) match {
          case Left(p) => p;
          case _ => assert(false);
        }

        builder = builder.set(j, i, pixel) match {
          case Left(b) => b;
          case _ => assert(false);
        }
      }
    }

    return builder.collect();
  }

  override def apply(img: Image[T]): Image[T] = {
    while (angle < 0) {
      angle += 360;
    }

    var result = img;
    while (angle != 0) {
      result = rotate_clockwise_90(result);
      angle -= 90;
    }

    return result;
  }
}

object Rotation {
  def apply[T <: Pixel](angle : Int): Either[Rotation[T], Error] = {
    if (angle % 90 != 0) {
      return Right(new Error("Rotation: Angle %d is not divisible by 90.".format(angle)));
    }

    return Left(new Rotation[T](angle));
  }
}
