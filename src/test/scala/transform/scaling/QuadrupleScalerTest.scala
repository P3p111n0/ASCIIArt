package transform.scaling;

import image.Image;
import image.data.ImageData;
import testing.MockPixel;
import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils;

class QuadrupleScalerTest extends AnyFunSuite {
  test("Scales") {
    for (_ <- 0 until TestUtils.nshots) {
      val (width, height) = TestUtils.get_random_dims();
      val vec = TestUtils.get_random_vector(width, height);
      val data = ImageData(vec) match {
        case Left(value) => value;
        case Right(err) => fail(err.msg);
      }
      val image = Image(data);
      val transform = new QuadrupleScaler[MockPixel]();
      val scaled = transform(image);
      assert(scaled.width() == width * 2);
      assert(scaled.height() == height * 2);
      for (pos <- scaled.iterate()) {
        val row = pos.row / 2;
        val col = pos.col / 2;
        val target = data.at(row, col) match {
          case Some(value) => value;
          case None => fail("QuadrupleScalerTest: Couldn't find pixel at (%d, %d).".format(row, col));
        }
        if (pos.value != target) {
          fail("QuadrupleScalerTest: Pixel at (%d, %d) doesn't match target at (%d, %d).".format(pos.row, pos.col, row, col));
        }
      }
    }
  }

  test("Scales 1x1") {
    val data = ImageData(Vector(Vector(MockPixel(0)))) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuadrupleScaler[MockPixel]();
    val scaled = transform(image);

    assert(scaled.width() == 2);
    assert(scaled.height() == 2);

    for (pos <- scaled.iterate()) {
      assert(pos.value == MockPixel(0));
    }
  }

  test("Scales 1x2") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1)))
    ) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuadrupleScaler[MockPixel]();
    val scaled = transform(image);

    assert(scaled.width() == 2);
    assert(scaled.height() == 4);

    val scaled_data = scaled.data;
    assert(scaled_data.at(0, 0).contains(MockPixel(0)));
    assert(scaled_data.at(0, 1).contains(MockPixel(0)));
    assert(scaled_data.at(0, 2).contains(MockPixel(1)));
    assert(scaled_data.at(0, 3).contains(MockPixel(1)));
    assert(scaled_data.at(1, 0).contains(MockPixel(0)));
    assert(scaled_data.at(1, 1).contains(MockPixel(0)));
    assert(scaled_data.at(1, 2).contains(MockPixel(1)));
    assert(scaled_data.at(1, 3).contains(MockPixel(1)));
  }

  test("Scales 2x2") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1)),
      Vector(MockPixel(2), MockPixel(3))
    )) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuadrupleScaler[MockPixel]();
    val scaled = transform(image);

    assert(scaled.width() == 4);
    assert(scaled.height() == 4);

    val scaled_data = scaled.data;
    assert(scaled_data.at(0, 0).contains(MockPixel(0)));
    assert(scaled_data.at(0, 1).contains(MockPixel(0)));
    assert(scaled_data.at(0, 2).contains(MockPixel(1)));
    assert(scaled_data.at(0, 3).contains(MockPixel(1)));
    assert(scaled_data.at(1, 0).contains(MockPixel(0)));
    assert(scaled_data.at(1, 1).contains(MockPixel(0)));
    assert(scaled_data.at(1, 2).contains(MockPixel(1)));
    assert(scaled_data.at(1, 3).contains(MockPixel(1)));
    assert(scaled_data.at(2, 0).contains(MockPixel(2)));
    assert(scaled_data.at(2, 1).contains(MockPixel(2)));
    assert(scaled_data.at(2, 2).contains(MockPixel(3)));
    assert(scaled_data.at(2, 3).contains(MockPixel(3)));
    assert(scaled_data.at(3, 0).contains(MockPixel(2)));
    assert(scaled_data.at(3, 1).contains(MockPixel(2)));
    assert(scaled_data.at(3, 2).contains(MockPixel(3)));
    assert(scaled_data.at(3, 3).contains(MockPixel(3)));
  }
}