package transform.grayscale;

import image.pixel.RGBPixel;
import org.scalatest.funsuite.AnyFunSuite;
import testing.MockGrayscale;

class BrightnessModifierTest extends AnyFunSuite {
  test("Modifies") {
    var pixel = RGBPixel();
    val modifier = new BrightnessModifier(10, MockGrayscale);
    val negative_modifier = new BrightnessModifier(-10, MockGrayscale);

    assert(modifier(pixel) == 10);
    assert(negative_modifier(pixel) == 0);

    pixel = RGBPixel(255, 255, 255) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    assert(modifier(pixel) == 255);
    assert(negative_modifier(pixel) == 245);

    pixel = RGBPixel(127, 85, 13) match {
        case Left(value)  => value;
        case Right(value) => fail(value.msg);
    }
    assert(modifier(pixel) == 99);
    assert(negative_modifier(pixel) == 79);
  }
}