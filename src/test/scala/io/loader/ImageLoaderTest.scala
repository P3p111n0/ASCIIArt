package io.loader

import org.scalatest.funsuite.AnyFunSuite;
import image.pixel.RGBPixel;
import image.ImageBuilder
import javax.imageio.ImageIO;
import java.io.File; 
import image.pixel.encoding.RGBEncoder;
import image.pixel.encoding.RGBDecoder;

class ImageLoaderTest extends AnyFunSuite {
  val test_images = List("./src/test/static/bobinka.png");
  
  test("Constructs"){
    for (img <- test_images) {
      ImageLoader(img, RGBDecoder) match {
        case Right(e) => fail("ImageLoader failed to construct with error: %s".format(e.msg));
        case _ =>
      }
    }
  }

  test("Fails") {
    val doesnt_exist = "./this/path/doesnt/exist"; 
    val not_a_file = "./src";
    val not_an_image = "./README.md";
    
    ImageLoader(doesnt_exist, RGBDecoder) match {
      case Right(_) =>
      case _ => fail("ImageLoader constructed from a non-existing file.");
    }
    
    ImageLoader(not_a_file, RGBDecoder) match {
      case Right(_) =>
      case _ => fail("ImageLoader constructed from a directory.");
    }
    
    ImageLoader(doesnt_exist, RGBDecoder) match {
      case Right(_) =>
      case _ => fail("ImageLoader constructed from a file thats not an image.");
    }
  }

  test("Loads") {
    for (img <- test_images) {
      val ref_pixels = ImageIO.read(new File(img));
      val loader = ImageLoader(img, RGBDecoder) match {
        case Right(e) => fail("Load test: ImageLoader failed to construct with error: %s".format(e.msg));
        case Left(x) => x;
      }
      val loaded_image = loader.load() match {
        case Right(e) => fail("Failed to load image %s with error: %s".format(img, e.msg));
        case Left(i) => i;
      } 

      val builder = ImageBuilder(loaded_image);
      assert(builder.get_width() == ref_pixels.getWidth());
      for (i <- 0 until ref_pixels.getWidth()) {
        assert(builder.get_height() == ref_pixels.getHeight());
        for (j <- 0 until ref_pixels.getHeight()) {
          val ref_pixel = ref_pixels.getRGB(i, j) & 0x00ffffff; // bitmask removes alpha component  
          val pix = builder.get(i, j) match {
            case Left(value) => value;
            case Right(value) => fail(value.msg);
          }
          val tested_pixel = RGBEncoder(pix);
          if (ref_pixel != tested_pixel) {
            fail("Load test: pixels at offset (%d, %d) don't match.".format(i, j));
          }
        }
      }
    }
  }
}
