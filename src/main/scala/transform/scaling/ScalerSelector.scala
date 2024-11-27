package transform.scaling;

import image.pixel.Pixel;
import image.Image;
import error.Error;
import org.scalatest.matchers.dsl.LengthWord

class ScalerSelector[T <: Pixel] private (private val scaler: Scaler[T]) extends Scaler[T] {
  override def apply(img: Image[T]): Image[T] = {
    return scaler(img);
  }
}

object ScalerSelector {
  def apply[T <: Pixel](factor : Double): Either[ScalerSelector[T], Error] = {
    factor match {
      case 1 => Left(new ScalerSelector(new OriginalScaler[T])); 
      case 4 => Left(new ScalerSelector(new QuadrupleScaler[T])); 
      case 0.25 => Left(new ScalerSelector(new QuarterScaler[T])); 
      case _ => return Right(new Error(s"Invalid scaling factor: $factor"));
    }
  }
}
