package transform.flip;

import image.Image;
import image.data.ImageData;
import utils.{TestUtils, MockPixel};
import org.scalatest.funsuite.AnyFunSuite;

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
}