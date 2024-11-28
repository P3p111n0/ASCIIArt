package transform.grayscale

import org.scalatest.funsuite.AnyFunSuite;
import image.pixel.RGBPixel
import testing.{MockGrayscale, TestUtils};

class GrayscaleEncoderTest extends AnyFunSuite {
  test("Encodes") {
    var pixel = RGBPixel();
    var result = GrayscaleEncoder(pixel);
    assert(result == MockGrayscale(pixel));

    pixel = RGBPixel(255, 255, 255) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    result = GrayscaleEncoder(pixel);
    assert(result == MockGrayscale(pixel));

    pixel = RGBPixel(127, 85, 13) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    result = GrayscaleEncoder(pixel);
    assert(result == MockGrayscale(pixel));
  }
}