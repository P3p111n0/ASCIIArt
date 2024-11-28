package io.loader;

import image.Image;
import error.Error;
import image.pixel.RGBPixel;
import image.pixel.encoding.Decoder;
import java.io.File;
import utils.FileUtils;

class JPGLoader(path : String, decoder : Decoder[Int, RGBPixel]) extends ImageLoader {
  override def load(): Either[Image[RGBPixel], error.Error] = {
    val f = new File(path);
    FileUtils.get_file_ext(f) match {
      case Some("jpg") =>
      case _ => return Right(new Error(s"${f.getCanonicalPath()} is not a .jpg file."));
    }
    return load_image(f, decoder); 
  }
}
