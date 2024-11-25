package transform.flip;

import image.Image;
import image.data.ImageData;
import utils.{TestUtils, MockPixel};
import org.scalatest.funsuite.AnyFunSuite;

class XFlipTest extends AnyFunSuite {
  test("Flips") {
    for (_ <- 0 until TestUtils.nshots) {
      val (width, height) = TestUtils.get_random_dims();
      val vec = TestUtils.get_random_vector(width, height);
      val data = ImageData(vec) match {
        case Left(value) => value;
        case Right(err) => fail(err.msg);
      }
      val image = Image(data);
      val transform = new XFlip[MockPixel]();
      val flipped = transform(image);
      for (pos <- flipped.iterate()) {
        val new_row = width - 1 - pos.row;
        val target = data.at(new_row, pos.col) match {
          case Some(value) => value;
          case None => fail("XFlipTest: Couldn't find pixel at (%d, %d).".format(new_row, pos.col));
        }
        if (pos.value != target) {
          fail("XFlipTest: Pixel at (%d, %d) doesn't match target at (%d, %d).".format(pos.row, pos.col, new_row, pos.col));
        }
      }
    }
  }
}