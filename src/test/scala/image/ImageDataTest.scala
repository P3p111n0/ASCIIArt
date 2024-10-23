package image;

import org.scalatest.funsuite.AnyFunSuite
import scala.util.Random
import scala.Vector
import image.pixel.RGBPixel

class ImageDataTest extends AnyFunSuite {
  val gen = Random();

  private def get_random_pixel(): RGBPixel = {
      val r = gen.between(0, 256);
      val g = gen.between(0, 256);
      val b = gen.between(0, 256);

      val pixel = RGBPixel(r, g, b);
      assert(pixel.nonEmpty);
      return pixel.get;
  }

  private def get_random_vector(width : Int, height : Int) : Vector[Vector[RGBPixel]] = {
      var arr = Vector[Vector[RGBPixel]]()
      for (i <- 0 to width) {
        var inner = Vector[RGBPixel]();
        for (i <- 0 to height) {
          inner = inner :+ get_random_pixel();
        }
        arr = arr :+ inner;
      }
      return arr;
  }

  test("Constructs") {
      val ntests = 9;
      for (i <- 0 to ntests) {
          val width = gen.between(10, 50);
          val height = gen.between(10, 50);
          val arr = get_random_vector(width, height);
          val obj = ImageData(arr);
          ImageData(arr) match {
              case Some(img) => {
                  assert(img.height == height + 1);
                  assert(img.width == width + 1);
                  assert(img.height - 1 == img.get_height_iter());
                  assert(img.width - 1 == img.get_width_iter());
              }
              case _ => fail("Failed to construct ImageData.");
          }
      }
  }

  test("Fails") {
      var arr = Vector[Vector[RGBPixel]]();
      assert(ImageData(arr).isEmpty);

      arr = Vector( Vector[RGBPixel]() );
      assert(ImageData(arr).isEmpty);

      arr = Vector( Vector(get_random_pixel(), get_random_pixel(), get_random_pixel()),
        Vector(get_random_pixel(), get_random_pixel()),
        Vector(get_random_pixel(), get_random_pixel(), get_random_pixel()) );
      assert(ImageData(arr).isEmpty);
    }

  test("At") {
      val width = gen.between(10, 50);
      val height = gen.between(10, 50);
      val arr = get_random_vector(width, height);
      val img_opt = ImageData(arr);
      assert(img_opt.nonEmpty);
      val img = img_opt.get;
      for (i <- 0 to img.get_width_iter()) {
          for (j <- 0 to img.get_height_iter()) {
              img.at(i, j) match {
                  case Some(elem) => {
                      assert(arr(i)(j) == elem);
                  }
                  case _ => fail("Elements at [%d, %d] don't match.".format(i, j));
              }
          }
      }  
  }  
}




