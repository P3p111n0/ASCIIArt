package io.encoding;

import org.scalatest.funsuite.AnyFunSuite;
import image.pixel.RGBPixel;
import utils.TestUtils;
import io.encoding.RGBEncoder;

class RGBEncoderTest extends AnyFunSuite {
  test("RGB Encoding"){
    val ntests = 10; 

    for (i <- 0 until ntests) {
      val pixel = TestUtils.get_random_rgb_pixel();
      val res = RGBEncoder(pixel);
      
      assert((res & 0xff000000) == 0);
      assert((res & 0xff0000) >> 16 == pixel.r);
      assert((res & 0xff00) >> 8 == pixel.g);
      assert((res & 0xff) == pixel.b);
    }
  }
}
