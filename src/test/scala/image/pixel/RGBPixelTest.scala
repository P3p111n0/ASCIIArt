package image.pixel 

import org.scalatest.funsuite.AnyFunSuite
import scala.util.Random

class RGBPixelTest extends AnyFunSuite {
  test("Constructs"){
    val ntests = 9;
    val gen = new Random;

    for (i <- 0.to(ntests)) {
      val r = gen.between(0, 256);
      val g = gen.between(0, 256);
      val b = gen.between(0, 256);

      RGBPixel(r, g, b) match {
        case Some(_) =>
        case _ => fail("Failed to construct RGBPixel with params (%d, %d, %d)".format(r, g, b));
      }
    }
  }

  test("Fails") {
    val gen = new Random;

    var r = -1;
    var g = gen.between(0, 256);
    var b = gen.between(0, 256);

    assert(RGBPixel(r, g, b).isEmpty);

    r = gen.between(0, 256);
    g = 67128736;

    assert(RGBPixel(r, g, b).isEmpty);
  }
}
