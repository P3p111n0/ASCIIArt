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
    var builder = ImageBuilder(og_builder.get_width(), og_builder.get_height(), ASCIIPixel()) match {
      case Left(b) => b;
      case _ => assert(false) // this shouldn't happen
    }

    for (i <- 0 until builder.get_width()) {
      for (j <- 0 until builder.get_height()) {
        val og_pixel = og_builder.get(i, j) match {
          case Left(p) => p;
          case _ => assert(false); // this shouldn't happen, we are always in bounds
        };
        builder = builder.set(i, j, transform(og_pixel)) match {
          case Left(b) => b;
          case _ => assert(false); // this shouldn't happen
        };
      }
    }

    return builder.collect();  
  }
}
