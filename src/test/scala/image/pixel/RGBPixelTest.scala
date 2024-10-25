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
        case Left(_) =>
        case _ => fail("Failed to construct RGBPixel with params (%d, %d, %d)".format(r, g, b));
      }
    }
  }

  test("Fails") {
    val gen = new Random;

    var r = -1;
    var g = gen.between(0, 256);
    var b = gen.between(0, 256);
    
    RGBPixel(r, g, b) match {
      case Right(_) =>
      case _ => fail("Invalid RGB pixel constructed with values (%d, %d, %d)".format(r, g, b));
    }

    r = gen.between(0, 256);
    g = 67128736;

    RGBPixel(r, g, b) match {
      case Right(_) =>
      case _ => fail("Invalid RGB pixel constructed with values (%d, %d, %d)".format(r, g, b));
    }
  }
}
