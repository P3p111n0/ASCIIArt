package io.saver;

import image.Image
import error.Error
import image.pixel.Pixel
import image.pixel.encoding.Encoder
import image.pixel.ASCIIPixel
import java.io.OutputStream
import java.io.IOException
import image.ImageBuilder
import error.InternalException

/**
 * A trait specifying the interface for saving an image to a stream.
 */
trait StreamSaver extends Saver[ASCIIPixel] {
  /**
   * Write an image to a stream.
   *
   * @param img    The image to write.
   * @param stream The stream to write to.
   * @return An error if the image could not be written properly.
   */
  protected def write_to_stream(img: Image[ASCIIPixel], stream: OutputStream): Option[Error] = {
    val builder = ImageBuilder(img);

    try {
      for (i <- 0 until builder.get_width()) {
        for (j <- 0 until builder.get_height()) {
          val pixel = builder.get(i, j) match {
            case Left(value) => value;
            case _ => throw new InternalException("StreamSaver: Failed to get at (%d, %d)".format(i, j));
          }
          stream.write(pixel.c);
        }
        stream.write('\n');
      }
    } catch {
      case e: Exception => return Some(new Error(e.toString));
      case internal: InternalException => throw internal;
    }
    None;
  }
}
