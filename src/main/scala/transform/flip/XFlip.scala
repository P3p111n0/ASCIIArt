package transform.flip;

import image.pixel.Pixel
import image.Image
import image.ImageBuilder

class XFlip[T <: Pixel] extends Flip[T] {
  override def apply(img: Image[T]): Image[T] = {
    var builder = ImageBuilder(img);

    return builder.collect();
  }
}
