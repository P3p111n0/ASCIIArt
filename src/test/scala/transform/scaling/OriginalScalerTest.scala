package transform.scaling;

import image.Image;
import image.data.ImageData;
import testing.MockPixel;
import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils;

class OriginalScalerTest extends AnyFunSuite {
  test("Scales") {
    for (_ <- 0 until TestUtils.nshots) {
      val (width, height) = TestUtils.get_random_dims();
      val vec = TestUtils.get_random_vector(width, height);
      val data = ImageData(vec) match {
        case Left(value) => value;
        case Right(err) => fail(err.msg);
      }
      val image = Image(data);
      val transform = new OriginalScaler[MockPixel]();
      val scaled = transform(image);
      assert(scaled.width() == width);
      assert(scaled.height() == height);
      for (pos <- scaled.iterate()) {
        val target = data.at(pos.row, pos.col) match {
          case Some(value) => value;
          case None => fail("OriginalScalerTest: Couldn't find pixel at (%d, %d).".format(pos.row / 2, pos.col / 2));
        }
        if (pos.value != target) {
          fail("OriginalScalerTest: Pixel at (%d, %d) doesn't match target at (%d, %d).".format(pos.row, pos.col, pos.row / 2, pos.col / 2));
        }
      }
    }
  }
}