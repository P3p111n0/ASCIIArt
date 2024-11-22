package image.pixel.encoding;

import org.scalatest.funsuite.AnyFunSuite;
import image.pixel.RGBPixel;
import utils.TestUtils;
import image.pixel.encoding.RGBEncoder;

class RGBDecoderTest extends AnyFunSuite {
  test("RGB Decoding"){
    val ntests = 10; 

    for (i <- 0 until ntests) {
      val value = TestUtils.get_random_int();
      val r = (value & 0xff0000) >> 16;
      val g = (value & 0xff00) >> 8;
      val b = value & 0xff;

      RGBDecoder(value) match {
        case RGBPixel(r, g, b) =>
        case RGBPixel(r_, g_, b_) => fail("RGB Decoding failed: %x decoded as (%d, %d, %d)".format(value, r_, g_, b_)); 
      }
      
      val value_without_alpha = value & 0x00ffffff; 
      RGBDecoder(value_without_alpha) match {
        case RGBPixel(r, g, b) =>
        case _ => fail("RGB Decoding failed: %x and %x decode differently.".format(value, value_without_alpha)); 
      }
    }
  }
}
