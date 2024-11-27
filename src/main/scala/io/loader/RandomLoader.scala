package io.loader;

import image.pixel.generator.Generator;
import loader.Loader;
import image.Image;
import error.Error;
import image.pixel.Pixel
import image.ImageBuilder
import error.InternalException
import scala.util.Random;

class RandomLoader[T <: Pixel](private val seed : Int, private val pixel_genenerator : Generator[T]) extends Loader[T] {
  private val gen = new Random(seed);
  override def load(): Either[Image[T], Error] = {
    val width = gen.between(100, 1000);
    val height = gen.between(100, 1000);
    val fill = pixel_genenerator.next();
    val builder = ImageBuilder(width, height, fill) match {
      case Left(b) => b;
      case Right(e) => throw new InternalException(e.msg);
    }
    val transform = (x : T) => pixel_genenerator.next();
    return Left(builder.map(transform).collect());
  }
} 
