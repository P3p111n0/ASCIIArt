package io.saver;

import image.Image
import error.Error
import image.pixel.Pixel
import io.encoding.Encoder
import image.pixel.ASCIIPixel

object ConsoleSaver extends StreamSaver {
  override def save(img: Image[ASCIIPixel]): Option[Error] = {
    write_to_stream(img, System.out); 
    return None;
  }
}
