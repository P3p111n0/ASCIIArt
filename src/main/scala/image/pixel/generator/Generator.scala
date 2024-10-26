package image.pixel.generator;

import image.pixel.Pixel;
import scala.util.Random;

trait Generator[T <: Pixel] {
  protected val gen = new Random;
  def next(): T;
}
