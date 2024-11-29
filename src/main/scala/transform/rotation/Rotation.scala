package transform.rotation

import error.{Error, InternalException}
import image.{Image, ImageBuilder}
import image.pixel.Pixel
import transform.Transformation

class Rotation[T <: Pixel] private(val angle: Int) extends AbstractRotation[T] {
  private def rotate_clockwise_90(img: Image[T]): Image[T] = {
    val fill: T = img.iterate().next().value;
    var builder = ImageBuilder[T](img.height(), img.width(), fill) match {
      case Left(b) => b;
      case _ => throw new InternalException("Rotation: Failed to construct builder.");
    }

    for (pos <- img.iterate()) {
      builder = builder.set(pos.col, pos.row, pos.value) match {
        case Left(b) => b;
        case _ => throw new InternalException("Rotation: Failed to set at (%d, %d).".format(pos.col, pos.row));
      }
    }

    return builder.collect();
  }

  override def apply(img: Image[T]): Image[T] = {
    var new_angle = angle;
    while (new_angle < 0) {
      new_angle += 360;
    }

    var result = img;
    while (new_angle != 0) {
      result = rotate_clockwise_90(result);
      new_angle -= 90;
    }

    return result;
  }
}

object Rotation {
  def apply[T <: Pixel](angle: Int): Either[Rotation[T], Error] = {
    if (angle % 90 != 0) {
      return Right(new Error("Rotation: Angle %d is not divisible by 90.".format(angle)));
    }

    return Left(new Rotation[T](angle));
  }
}
