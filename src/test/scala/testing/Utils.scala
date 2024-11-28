package testing

import image.Image
import image.data.ImageData
import image.pixel.RGBPixel
import testing.MockPixel

import scala.util.Random;

object TestUtils {
  private val seed = 333;
  private val gen = new Random(seed);
  val nshots = 10;

  def get_test_images_path(): String = {
    return "./src/test/static/";
  }
  
  def get_int_in_range(min : Int, max : Int): Int = {
    return gen.between(min, max);
  }

  def get_random_pixel(): MockPixel = {
    return new MockPixel(get_random_int());
  }

  def get_random_rgb_pixel(): RGBPixel = {
    val r = get_int_in_range(0, 256)
    val g = get_int_in_range(0, 256)
    val b = get_int_in_range(0, 256)
    RGBPixel(r, g, b) match {
        case Left(value) => return value;
        case Right(value) => assert(false);
    }
  }

  def get_random_int(): Int = {
    return gen.nextInt();
  }

  def get_random_dims(): (Int, Int) = {
    return (gen.between(1, 250), gen.between(1, 250));
  }

  def get_inbounds_dims(w : Int, h : Int): (Int, Int) = {
    return (gen.between(0, w), gen.between(0, h));
  }

  def get_random_vector(width : Int, height : Int) : Vector[Vector[MockPixel]] = {
      var arr = Vector[Vector[MockPixel]]()
      for (i <- 0 until width) {
        var inner = Vector[MockPixel]();
        for (_ <- 0 until height) {
          inner = inner :+ get_random_pixel();
        }
        arr = arr :+ inner;
      }
      return arr;
  }

  def get_random_image(): Image[MockPixel] = {
    val dims = get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val vec = get_random_vector(width, height);
    val data = ImageData(vec) match {
      case Left(value) => value;
      case Right(value) => assert(false);
    }
    return Image(data);
  }

  def get_random_rgb_vector(width : Int, height : Int) : Vector[Vector[RGBPixel]] = {
      var arr = Vector[Vector[RGBPixel]]()
      for (i <- 0 until width) {
        var inner = Vector[RGBPixel]();
        for (_ <- 0 until height) {
          inner = inner :+ get_random_rgb_pixel();
        }
        arr = arr :+ inner;
      }
      return arr;
  }

  def get_random_rgb_image(): Image[RGBPixel] = {
    val dims = get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val vec = get_random_rgb_vector(width, height);
    val data = ImageData(vec) match {
      case Left(value) => value;
      case Right(value) => assert(false);
    }
    return Image(data);
  }
}
