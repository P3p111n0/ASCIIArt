package image.data;

import org.scalatest.funsuite.AnyFunSuite
import scala.util.Random
import scala.Vector
import org.scalatest.matchers.must.Matchers
import utils.{TestUtils, MockPixel}

class ImageDataTest extends AnyFunSuite {
  test("Constructs") {
    for (i <- 0 to TestUtils.nshots) {
      val dims = TestUtils.get_random_dims();
      val width = dims(0);
      val height = dims(1);
      val arr = TestUtils.get_random_vector(width, height);
      val obj = ImageData(arr);
      ImageData(arr) match {
        case Left(img) =>
          assert(img.height() == height);
          assert(img.width() == width);
        case _ => fail("Failed to construct ImageData.");
      }
    }
  }

  test("Fails") {
    var arr = Vector[Vector[MockPixel]]();
    ImageData(arr) match {
      case Right(_) =>
      case _        => fail("ImageData constructed on an empty vector.");
    }

    arr = Vector(Vector[MockPixel]());
    ImageData(arr) match {
      case Right(_) =>
      case _        => fail("ImageData constructed with empty columns.");
    }

    arr = Vector(
      Vector(
        TestUtils.get_random_pixel(),
        TestUtils.get_random_pixel(),
        TestUtils.get_random_pixel()),
      Vector(
        TestUtils.get_random_pixel(),
        TestUtils.get_random_pixel()),
      Vector(
        TestUtils.get_random_pixel(),
        TestUtils.get_random_pixel(),
        TestUtils.get_random_pixel())
    );
    ImageData(arr) match {
      case Right(_) =>
      case _        => fail("ImageData constructed with column height mismatch.");
    }
  }

  test("At") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val arr = TestUtils.get_random_vector(width, height);
    val img_opt = ImageData(arr);
    img_opt match {
      case Right(e) => fail(e.msg);
      case Left(img) =>
        for (i <- 0 until img.width())
          for (j <- 0 until img.height())
            img.at(i, j) match {
              case Some(elem) =>
                assert(arr(i)(j) == elem);
              case _ => fail("Elements at [%d, %d] don't match.".format(i, j));
            }
    }
  }

  test("Maps") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val arr = TestUtils.get_random_vector(width, height);
    val img_opt = ImageData(arr);
    val transform = (p: MockPixel) => new MockPixel(p.value * 8 / 5);
    img_opt match {
      case Right(e) => fail(e.msg);
      case Left(img) => {
        val new_img = img.map(transform);
        for (i <- 0 until img.width())
          for (j <- 0 until img.height()) {
            val pix = img.at(i, j).get
            assert(new_img.at(i, j).get == transform(pix));
          }
      }
    }
  }
}
