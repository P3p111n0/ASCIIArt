package testing

import image.pixel.encoding.Encoder
import image.pixel.{ASCIIPixel, RGBPixel}
import transform.PixelTransform
import transform.ascii.ASCIIMap;

class MockRgbAsciiTransform(val to_grayscale : Encoder[RGBPixel, Int], val to_ascii : ASCIIMap[Int]) extends PixelTransform[RGBPixel, ASCIIPixel] {
  def apply(pixel: RGBPixel): ASCIIPixel = {
    return to_ascii(to_grayscale(pixel));
  }
}