package io.saver;

import image.Image
import error.Error
import image.pixel.Pixel
import image.pixel.encoding.Encoder
import image.pixel.ASCIIPixel
import java.io.File
import java.io.FileOutputStream

class TextFileSaver(val path : String) extends StreamSaver {
  override def save(img: Image[ASCIIPixel]): Option[Error] = {
    val f = new File(path);
    if (f.exists() && !f.canWrite()) {
      return Some(new Error("Cannot write to file: %s".format(path)));
    }

    val stream = new FileOutputStream(f);
    return write_to_stream(img, stream);
  }
}
