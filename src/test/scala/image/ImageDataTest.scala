package image.data;

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
      return pixel.left.getOrElse(fail("This shouldn't happen."));
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
              case Left(img) => {
                  assert(img.height() == height + 1);
                  assert(img.width() == width + 1);
              }
              case _ => fail("Failed to construct ImageData.");
          }
      }
  }

  test("Fails") {
      var arr = Vector[Vector[RGBPixel]]();
      ImageData(arr) match {
        case Right(_) =>
        case _ => fail("ImageData constructed on an empty vector.");
      }

      arr = Vector( Vector[RGBPixel]() );
      ImageData(arr) match {
        case Right(_) =>
        case _ => fail("ImageData constructed with empty columns.");
      }

      arr = Vector( Vector(get_random_pixel(), get_random_pixel(), get_random_pixel()),
        Vector(get_random_pixel(), get_random_pixel()),
        Vector(get_random_pixel(), get_random_pixel(), get_random_pixel()) );
      ImageData(arr) match {
        case Right(_) =>
        case _ => fail("ImageData constructed with column height mismatch.");
      }
    }

  test("At") {
      val width = gen.between(10, 50);
      val height = gen.between(10, 50);
      val arr = get_random_vector(width, height);
      val img_opt = ImageData(arr);
      img_opt match {
        case Right(e) => fail(e.msg);
        case Left(img) => {
          for (i <- 0 until img.width()) {
            for (j <- 0 until img.height()) {
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
  }  
}




