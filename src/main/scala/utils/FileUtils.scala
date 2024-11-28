package utils;

import java.io.File; 
import java.nio.file.Files;
import error.Error;

object FileUtils {
  def get_file_ext(f : File): Option[String] = {
    val fileName = f.getCanonicalPath();
    val i = fileName.lastIndexOf('.');
    if (i > 0) {
        return Some(fileName.substring(i+1));
    }
    return None;
  }

  def get_file_ext(path : String): Option[String] = {
    val f = new File(path); 
    return get_file_ext(f);
  }

  def is_image_file(f : File): Boolean = {
    // https://stackoverflow.com/questions/9643228/test-if-a-file-is-an-image-file
    val mimetype = Files.probeContentType(f.toPath());
    if (mimetype != null && mimetype.split("/")(0).equals("image")) {
      return true;
    }
    return false;
  }

  def validate_image_file(f : File): Option[Error] = {
    if (!f.exists()) {
      return Some(new Error(s"File ${f.getCanonicalPath()} doesn't exist."));
    } else if (!f.isFile()) {
      return Some(new Error(s"${f.getCanonicalPath()} is not a regular file."));
    } else if (!is_image_file(f)) {
      return Some(new Error(s"${f.getCanonicalPath()} is not an image."));
    }
    return None;
  }

}
