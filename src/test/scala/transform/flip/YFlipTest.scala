package transform.flip;

import image.Image;
import image.data.ImageData;
import testing.MockPixel;
import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils;

class YFlipTest extends AnyFunSuite {
  test("Flips") {
    for (_ <- 0 until TestUtils.nshots) {
      val (width, height) = TestUtils.get_random_dims();
      val vec = TestUtils.get_random_vector(width, height);
      val data = ImageData(vec) match {
        case Left(value) => value;
        case Right(err) => fail(err.msg);
      }
      val image = Image(data);
      val transform = new YFlip[MockPixel]();
      val flipped = transform(image);
      for (pos <- flipped.iterate()) {
        val new_col = height - 1 - pos.col;
        val target = data.at(pos.row, new_col) match {
          case Some(value) => value;
          case None => fail("YFlipTest: Couldn't find pixel at (%d, %d).".format(pos.row, new_col));
        }
        if (pos.value != target) {
          fail("YFlipTest: Pixel at (%d, %d) doesn't match target at (%d, %d).".format(pos.row, pos.col, pos.row, new_col));
        }
      }
    }
  }

  test("Flips 1x2") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1))
    )) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new YFlip[MockPixel]();
    val flipped = transform(image);

    assert(image.width() == flipped.width());
    assert(image.height() == flipped.height());

    assert(flipped.data.at(0, 0).contains(MockPixel(1)));
    assert(flipped.data.at(0, 1).contains(MockPixel(0)));
  }

  test("Flips 2x2") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1)),
      Vector(MockPixel(2), MockPixel(3))
    )) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new YFlip[MockPixel]();
    val flipped = transform(image);

    assert(image.width() == flipped.width());
    assert(image.height() == flipped.height());

    assert(flipped.data.at(0, 0).contains(MockPixel(1)));
    assert(flipped.data.at(0, 1).contains(MockPixel(0)));
    assert(flipped.data.at(1, 0).contains(MockPixel(3)));
    assert(flipped.data.at(1, 1).contains(MockPixel(2)));
  }

  test("Flips 3x3") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1), MockPixel(2)),
      Vector(MockPixel(3), MockPixel(4), MockPixel(5)),
      Vector(MockPixel(6), MockPixel(7), MockPixel(8))
    )) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new YFlip[MockPixel]();
    val flipped = transform(image);

    assert(image.width() == flipped.width());
    assert(image.height() == flipped.height());

    assert(flipped.data.at(0, 0).contains(MockPixel(2)));
    assert(flipped.data.at(0, 1).contains(MockPixel(1)));
    assert(flipped.data.at(0, 2).contains(MockPixel(0)));
    assert(flipped.data.at(1, 0).contains(MockPixel(5)));
    assert(flipped.data.at(1, 1).contains(MockPixel(4)));
    assert(flipped.data.at(1, 2).contains(MockPixel(3)));
    assert(flipped.data.at(2, 0).contains(MockPixel(8)));
    assert(flipped.data.at(2, 1).contains(MockPixel(7)));
    assert(flipped.data.at(2, 2).contains(MockPixel(6)));
  }

  test("Flips 4x4") {
    val data = ImageData(Vector(
      Vector(MockPixel(0), MockPixel(1), MockPixel(2), MockPixel(3)),
      Vector(MockPixel(4), MockPixel(5), MockPixel(6), MockPixel(7)),
      Vector(MockPixel(8), MockPixel(9), MockPixel(10), MockPixel(11)),
      Vector(MockPixel(12), MockPixel(13), MockPixel(14), MockPixel(15))
    )) match {
      case Left(value) => value;
      case Right(err) => fail(err.msg);
    }
    val image = Image(data);
    val transform = new YFlip[MockPixel]();
    val flipped = transform(image);

    assert(image.width() == flipped.width());
    assert(image.height() == flipped.height());

    assert(flipped.data.at(0, 0).contains(MockPixel(3)));
    assert(flipped.data.at(0, 1).contains(MockPixel(2)));
    assert(flipped.data.at(0, 2).contains(MockPixel(1)));
    assert(flipped.data.at(0, 3).contains(MockPixel(0)));
    assert(flipped.data.at(1, 0).contains(MockPixel(7)));
    assert(flipped.data.at(1, 1).contains(MockPixel(6)));
    assert(flipped.data.at(1, 2).contains(MockPixel(5)));
    assert(flipped.data.at(1, 3).contains(MockPixel(4)));
    assert(flipped.data.at(2, 0).contains(MockPixel(11)));
    assert(flipped.data.at(2, 1).contains(MockPixel(10)));
    assert(flipped.data.at(2, 2).contains(MockPixel(9)));
    assert(flipped.data.at(2, 3).contains(MockPixel(8)));
    assert(flipped.data.at(3, 0).contains(MockPixel(15)));
    assert(flipped.data.at(3, 1).contains(MockPixel(14)));
    assert(flipped.data.at(3, 2).contains(MockPixel(13)));
    assert(flipped.data.at(3, 3).contains(MockPixel(12)));
  }
}