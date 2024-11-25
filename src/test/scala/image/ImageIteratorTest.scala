package image.iterator;

import org.scalatest.funsuite.AnyFunSuite;
import utils.{TestUtils, MockPixel}

class ImageIteratorTest extends AnyFunSuite {
  test("Constructs") {
    for (_ <- 0 until TestUtils.nshots) {
      val img = TestUtils.get_random_image();
      val dims = TestUtils.get_inbounds_dims(img.width(), img.height());
      val it = ImageIterator(img, dims(0), dims(1)) match {
        case Left(value) =>
        case _           => fail("Failed to create ImageIterator")
      }
    }
  }

  test("Fails") {
    val img = TestUtils.get_random_image();
    ImageIterator(img, -1, 0) match {
      case Right(_) =>
      case _        => fail("Failed to catch negative width")
    }

    ImageIterator(img, 0, -1) match {
      case Right(_) =>
      case _        => fail("Failed to catch negative height")
    }

    ImageIterator(img, img.width(), 0) match {
      case Right(_) =>
      case _        => fail("Failed to catch out of bounds dims")
    }

    ImageIterator(img, 0, img.height()) match {
      case Right(_) =>
      case _        => fail("Failed to catch out of bounds dims")
    }

    ImageIterator(img, img.width(), img.height()) match {
      case Right(_) =>
      case _        => fail("Failed to catch out of bounds dims")
    }
  }

  test("Iterates") {
    val img = TestUtils.get_random_image();
    val it = ImageIterator(img);
    var i = 0;
    var j = 0;

    for (elem <- it) {
      assert(elem.row == i);
      assert(elem.col == j);
      assert(elem.value == img.data.at(i, j).get);
      j += 1;
      if (j == img.height()) {
        j = 0;
        i += 1;
      }
    }
    assert(i == img.width() && j == 0);
  }
}