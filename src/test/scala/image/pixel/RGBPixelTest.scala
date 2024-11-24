package image.pixel 

import org.scalatest.funsuite.AnyFunSuite
import scala.util.Random
import utils.TestUtils

class RGBPixelTest extends AnyFunSuite {
  test("Constructs"){
    for (i <- 0 until TestUtils.nshots) {
      var r = TestUtils.get_int_in_range(0, 256);
      var g = TestUtils.get_int_in_range(0, 256);
      var b = TestUtils.get_int_in_range(0, 256);

      RGBPixel(r, g, b) match {
        case Left(_) =>
        case _ => fail("Failed to construct RGBPixel with params (%d, %d, %d)".format(r, g, b));
      }
    }
  }

  test("Fails") {
    var r = -1;
    var g = TestUtils.get_int_in_range(0, 256);
    var b = TestUtils.get_int_in_range(0, 256);

    RGBPixel(r, g, b) match {
      case Right(_) =>
      case _ => fail("Invalid RGB pixel constructed with values (%d, %d, %d)".format(r, g, b));
    }

    r = TestUtils.get_int_in_range(0, 256);
    g = 67128736;

    RGBPixel(r, g, b) match {
      case Right(_) =>
      case _ => fail("Invalid RGB pixel constructed with values (%d, %d, %d)".format(r, g, b));
    }
  }
}
