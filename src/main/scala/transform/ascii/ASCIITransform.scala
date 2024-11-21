package transform.ascii;

import transform.PixelTransform
import image.pixel.RGBPixel
import image.pixel.ASCIIPixel
import transform.Transformation
import image.Image
import image.ImageBuilder

class ASCIITransform(val transform : PixelTransform[RGBPixel, ASCIIPixel]) extends Transformation[RGBPixel, ASCIIPixel] {
  override def apply(img: Image[RGBPixel]): Image[ASCIIPixel] = {
    val og_builder = ImageBuilder(img);
    val builder = og_builder.map(transform.apply);
    return builder.collect();
  }
}
