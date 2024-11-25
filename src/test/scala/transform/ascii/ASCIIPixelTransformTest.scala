package transform.ascii;

import image.pixel.ASCIIPixel;
import image.pixel.RGBPixel;
import utils.{MockGrayscale, MockToASCIIPixel};
import org.scalatest.funsuite.AnyFunSuite;

class ToASCIITransformTest extends AnyFunSuite {
  test("Transforms") {
    val transform = new ToASCIITransform(MockGrayscale, MockToASCIIPixel);
    var pixel = RGBPixel(0, 0, 0) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    var target = ASCIIPixel(' ') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(transform(pixel) == target);

    pixel = RGBPixel(255, 255, 255) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    target = ASCIIPixel('$') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(transform(pixel) == target);

    pixel = RGBPixel(127, 85, 13) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    target = ASCIIPixel('/') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(transform(pixel) == target);
  }
}