package io.saver;

import image.Image
import error.Error
import image.pixel.Pixel
import io.encoding.Encoder
import image.pixel.ASCIIPixel
import java.io.OutputStream
import image.ImageBuilder

trait StreamSaver extends Saver[ASCIIPixel] {
  protected def write_to_stream(img: Image[ASCIIPixel], stream : OutputStream): Unit = {
    val builder = ImageBuilder(img);

    for (i <- 0 until builder.get_width()) {
      for (j <- 0 to builder.get_height()) {
        val pixel = builder.get(i, j) match {
          case Left(value) => value;
          case _ => assert(false);
        }
        stream.write(pixel.c);
      }
      stream.write("\n".toByte);
    }
  }
}
