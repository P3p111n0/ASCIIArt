package utils;

import image.pixel.{RGBPixel, ASCIIPixel};
import image.pixel.encoding.Encoder;
import transform.ascii.ASCIIMap;
import transform.PixelTransform;

class MockRgbAsciiTransform(val to_grayscale : Encoder[RGBPixel, Int], val to_ascii : ASCIIMap[Int]) extends PixelTransform[RGBPixel, ASCIIPixel] {
  def apply(pixel: RGBPixel): ASCIIPixel = {
    return to_ascii(to_grayscale(pixel));
  }
}