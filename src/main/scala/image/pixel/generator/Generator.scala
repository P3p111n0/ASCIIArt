package image.pixel.generator;

import image.pixel.Pixel;
import scala.util.Random;
import java.{util => ju}

trait Generator[T <: Pixel] {
  def next(): T;
}

trait PseudoRandomGen[T <: Pixel](private val seed : Int) extends Generator[T] {
  protected val gen = new Random(seed);
}
