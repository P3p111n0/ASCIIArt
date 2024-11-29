package transform.grayscale

import org.scalatest.funsuite.AnyFunSuite;
import image.pixel.RGBPixel;
import testing.MockGrayscale;

class GrayscaleInverterTest extends AnyFunSuite {
  test("Inverts") {
    var pixel = RGBPixel();
    var encoder = new GrayscaleInverter(MockGrayscale);

    assert(encoder(pixel) == 255);

    pixel = RGBPixel(255, 255, 255) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    assert(encoder(pixel) == 0);

    pixel = RGBPixel(127, 85, 13) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    assert(encoder(pixel) == 166);
  }
}