package image.pixel

import org.scalatest.funsuite.AnyFunSuite

class ASCIIPixelTest extends AnyFunSuite {
  test("constructs - valid") {
    ASCIIPixel('a') match {
      case Left(pixel) => assert(pixel.c == 'a');
      case Right(err) => fail(err.msg);
    }
  }

  test("constructs - valid 2") {
    ASCIIPixel(' ') match {
      case Left(pixel) => assert(pixel.c == ' ');
      case Right(err) => fail(err.msg);
    }
  }

  test("constructs - valid 3") {
    ASCIIPixel('~') match {
      case Left(pixel) => assert(pixel.c == '~');
      case Right(err) => fail(err.msg);
    }
  }

  test("constructs - invalid") {
    ASCIIPixel(0x1f.toChar) match {
      case Left(pixel) => fail("ASCIIPixelTest: Constructed invalid pixel.");
      case Right(err) => assert(err.msg == "ASCIIPixel: Character 0x1f is not printable");
    }
  }

  test("constructs - invalid 2") {
    ASCIIPixel(0x7f.toChar) match {
      case Left(pixel) => fail("ASCIIPixelTest: Constructed invalid pixel.");
      case Right(err) => assert(err.msg == "ASCIIPixel: Character 0x7f is not printable");
    }
  }

  test("constructs - invalid 3") {
    ASCIIPixel(0x80.toChar) match {
      case Left(pixel) => fail("ASCIIPixelTest: Constructed invalid pixel.");
      case Right(err) => assert(err.msg == "ASCIIPixel: Character 0x80 is not printable");
    }
  }

  test("default") {
    val pixel = ASCIIPixel();
    assert(pixel.c == 0);
  }
}
