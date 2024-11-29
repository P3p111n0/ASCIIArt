package transform.scaling;

import image.Image;
import image.data.ImageData;
import testing.MockPixel;
import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils;

class QuarterScalerTest extends AnyFunSuite {
  test("Scales") {
    for (_ <- 0 until TestUtils.nshots) {
      val (width, height) = TestUtils.get_random_dims();
      val vec = TestUtils.get_random_vector(width, height);
      val data = ImageData(vec) match {
        case Left(value) => value;
        case Right(err) => fail(err.msg);
      }
      val image = Image(data);
      val transform = new QuarterScaler[MockPixel]();
      val scaled = transform(image);
      assert(scaled.width() == width / 2 + (width % 2));
      assert(scaled.height() == height / 2 + (height % 2));
      for (pos <- scaled.iterate()) {
        val row = pos.row * 2;
        val col = pos.col * 2;
        val target = data.at(row, col) match {
          case Some(value) => value;
          case None => fail("QuarterScalerTest: Couldn't find pixel at (%d, %d).".format(row, col));
        }
        if (pos.value != target) {
          fail("QuarterScalerTest: Pixel at (%d, %d) doesn't match target at (%d, %d).".format(pos.row, pos.col, row, col));
        }
      }
    }
  }

  test("Scales 2x2") {
    val data = ImageData(Vector(Vector(MockPixel(0), MockPixel(1)), Vector(MockPixel(2), MockPixel(3)))) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuarterScaler[MockPixel]();
    val scaled = transform(image);
    assert(scaled.width() == 1);
    assert(scaled.height() == 1);
    assert(scaled.data.at(0, 0).contains(MockPixel(0)));
  }

  test("Scales 3x2") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1)),
      Vector(MockPixel(2), MockPixel(3)),
      Vector(MockPixel(4), MockPixel(5)))
    ) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuarterScaler[MockPixel]();
    val scaled = transform(image);

    assert(scaled.width() == 2);
    assert(scaled.height() == 1);

    assert(scaled.data.at(0, 0).contains(MockPixel(0)));
    assert(scaled.data.at(1, 0).contains(MockPixel(4)));
  }

  test("Scales 4x4") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1), MockPixel(2), MockPixel(3)),
      Vector(MockPixel(4), MockPixel(5), MockPixel(6), MockPixel(7)),
      Vector(MockPixel(8), MockPixel(9), MockPixel(10), MockPixel(11)),
      Vector(MockPixel(12), MockPixel(13), MockPixel(14), MockPixel(15)))
    ) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuarterScaler[MockPixel]();
    val scaled = transform(image);

    assert(scaled.width() == 2);
    assert(scaled.height() == 2);

    assert(scaled.data.at(0, 0).contains(MockPixel(0)));
    assert(scaled.data.at(0, 1).contains(MockPixel(2)));
    assert(scaled.data.at(1, 0).contains(MockPixel(8)));
    assert(scaled.data.at(1, 1).contains(MockPixel(10)));
  }

  test("Scales 1x1") {
    val data = ImageData(Vector(Vector(MockPixel(0)))) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new QuarterScaler[MockPixel]();
    val scaled = transform(image);
    assert(scaled.width() == 1);
    assert(scaled.height() == 1);
    assert(scaled.data.at(0, 0).contains(MockPixel(0)));
  }
}