package transform.ascii;

import image.pixel.ASCIIPixel;
import transform.ascii.ASCIIIntMap;
import org.scalatest.funsuite.AnyFunSuite;

class CustomASCIIMapTest extends AnyFunSuite {
  test("Translates to ASCII") {
    val map = new ASCIIIntMap("abcdefghijklmnopqrstuvwxyz");
    var pixel = ASCIIPixel('a') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(0) == pixel);

    pixel = ASCIIPixel('z') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(255) == pixel);

    pixel = ASCIIPixel('j') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(89) == pixel);

    pixel = ASCIIPixel('b') match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    }
    assert(map(11) == pixel);
  }
}