package io.loader;

import scala.Either;
import loader.Loader;
import javax.imageio.ImageIO;
import image.pixel.RGBPixel;
import image.Image;
import image.ImageBuilder;
import error.Error;
import scala.util.boundary, boundary.break;
import image.pixel.encoding.Decoder;
import utils.FileUtils
import java.io.File; 

trait ImageLoader extends Loader[RGBPixel] {
  protected def load_image(f : File, decoder : Decoder[Int, RGBPixel]): Either[Image[RGBPixel], Error] = {
    if (!FileUtils.is_image_file(f)) {
      return Right(new Error(s"${f.getCanonicalPath()} is not an image."));
    }

    FileUtils.validate_image_file(f) match {
      case Some(e) => return Right(e);
      case None =>
    }

    val loaded_image = ImageIO.read(f);
    val fill = RGBPixel();
    val builder_opt = ImageBuilder(loaded_image.getWidth(), loaded_image.getHeight(), fill);
    var builder: ImageBuilder[RGBPixel] = builder_opt match {
      case Right(e) => return Right(e);
      case Left(x) => x; 
    }

    val err : Option[Error] = boundary {
      for (i <- 0 until builder.get_width()) {
        for (j <- 0 until builder.get_height()) {
          val encoded_pixel = loaded_image.getRGB(j, i); // width is downwards ???????? idk why
          val pixel = decoder(encoded_pixel);
          builder.set(i, j, pixel) match {
            case Right(e) => break(Some(e));
            case Left(b) => builder = b;
          }
        }
      }
      None
    }

    err match {
      case Some(e) => return Right(e);
      case _ =>
    }

    return Left(builder.collect());
  }
}
