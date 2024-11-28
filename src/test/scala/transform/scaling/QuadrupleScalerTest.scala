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
}