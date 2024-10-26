package io.loader;

import scala.Either;
import loader.Loader;
import javax.imageio.ImageIO;
import java.io.File; 
import java.nio.file.Files;
import image.pixel.RGBPixel;
import image.Image;
import image.ImageBuilder;
import error.Error;
import scala.util.boundary, boundary.break;
import io.encoding.RGBDecoder;

class ImageLoader private (private val f : File) extends Loader[RGBPixel] {
    override def load(): Either[Image[RGBPixel], Error] = {
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
          val encoded_pixel = loaded_image.getRGB(i, j);
          val pixel = RGBDecoder(encoded_pixel);
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

object ImageLoader {
  private def is_image_file(f : File): Boolean = {
    // https://stackoverflow.com/questions/9643228/test-if-a-file-is-an-image-file
    val mimetype = Files.probeContentType(f.toPath());
    if (mimetype != null && mimetype.split("/")(0).equals("image")) {
      return true;
    }
    return false;
  }

  def apply(s : String): Either[ImageLoader, Error] = {
    val f = new File(s);

    if (!f.exists()) {
      return Right(new Error("File %s doesn't exist.".format(s)));
    } else if (!f.isFile()) {
      return Right(new Error("%s is not a regular file.".format(s)));
    } else if (!is_image_file(f)) {
      return Right(new Error("%s is not an image.".format(s)));
    }

    return Left(new ImageLoader(f));
  }
}
