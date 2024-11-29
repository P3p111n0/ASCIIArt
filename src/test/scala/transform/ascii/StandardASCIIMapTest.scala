package transform.ascii;

import image.pixel.ASCIIPixel;
import org.scalatest.funsuite.AnyFunSuite;

class StandardASCIIMapTest extends AnyFunSuite {
  test("Translates to ASCII") {
    val map = StandardASCIIMap;
    var pixel = ASCIIPixel(' ') match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    assert(map(0) == pixel);

    pixel = ASCIIPixel('$') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(255) == pixel);

    pixel = ASCIIPixel('/') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(89) == pixel);

    pixel = ASCIIPixel('.') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(4) == pixel);
  }
}