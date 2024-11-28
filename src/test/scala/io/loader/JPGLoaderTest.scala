package io.loader

import image.pixel.RGBPixel
import org.scalatest.funsuite.AnyFunSuite
import testing.{MockDecoder, TestUtils}

class JPGLoaderTest extends AnyFunSuite{
  private val base = TestUtils.get_test_images_path() + "jpg";
  test("red pixel") {
    val path = s"$base/red_pixel.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 1)
    assert(img.height() == 1)
    img.data.at(0, 0) match {
      case Some(RGBPixel(254, 0, 0)) =>
      case _ => fail("Red pixel not loaded correctly.");
    }
  }

  test("white pixel") {
    val path = s"$base/white_pixel.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 1)
    assert(img.height() == 1)
    img.data.at(0, 0) match {
      case Some(RGBPixel(255, 255, 255)) =>
      case _ => fail("White pixel not loaded correctly.");
    }
  }

  test("light blue pixel") {
    val path = s"$base/light_blue_pixel.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 1)
    assert(img.height() == 1)
    img.data.at(0, 0) match {
      case Some(RGBPixel(86, 156, 226)) =>
      case _ => fail("Light blue pixel not loaded correctly.");
    }
  }

  test("colours") {
    val path = s"$base/colours.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 3)
    assert(img.height() == 3)
    img.data.at(0, 0) match {
      case Some(RGBPixel(252, 0, 0)) =>
      case _ => fail("Red pixel not loaded correctly.");
    }
    val value = img.data.at(0, 1)
    img.data.at(0, 1) match {
      case Some(RGBPixel(0, 251, 6)) =>
      case _ => fail("Green pixel not loaded correctly.");
    }
    img.data.at(0, 2) match {
      case Some(RGBPixel(6, 3, 255)) =>
      case _ => fail("Blue pixel not loaded correctly.");
    }
    img.data.at(1, 0) match {
      case Some(RGBPixel(238, 216, 19)) =>
      case _ => fail("Yellow pixel not loaded correctly.");
    }
    img.data.at(1, 1) match {
      case Some(RGBPixel(10, 255, 194)) =>
      case _ => fail("Cyan pixel not loaded correctly.");
    }
    img.data.at(1, 2) match {
      case Some(RGBPixel(139, 10, 250)) =>
      case _ => fail("Purple pixel not loaded correctly.");
    }
    img.data.at(2, 0) match {
      case Some(RGBPixel(25, 182, 103)) =>
      case _ => fail("Dark green pixel not loaded correctly.");
    }
    img.data.at(2, 1) match {
      case Some(RGBPixel(255, 254, 248)) =>
      case _ => fail("White pixel not loaded correctly.");
    }
    img.data.at(2, 2) match {
      case Some(RGBPixel(10, 0, 0)) =>
      case _ => fail("Black pixel not loaded correctly.");
    }
  }

  test("bobinka") {
    val path = s"$base/bobinka.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 5)
    assert(img.height() == 5)
    img.data.at(0, 0) match {
      case Some(RGBPixel(10, 180, 247)) =>
      case _ => fail("Pixel at (0, 0) not loaded correctly.");
    }
    img.data.at(0, 1) match {
      case Some(RGBPixel(0, 184, 244)) =>
      case _ => fail("Pixel at (0, 1) not loaded correctly.");
    }
    img.data.at(0, 2) match {
      case Some(RGBPixel(255, 167, 170)) =>
      case _ => fail("Pixel at (0, 2) not loaded correctly.");
    }
    img.data.at(0, 3) match {
      case Some(RGBPixel(0, 181, 247)) =>
      case _ => fail("Pixel at (0, 3) not loaded correctly.");
    }
    img.data.at(0, 4) match {
      case Some(RGBPixel(0, 187, 228)) =>
      case _ => fail("Pixel at (0, 4) not loaded correctly.");
    }
    img.data.at(1, 0) match {
      case Some(RGBPixel(0, 186, 241)) =>
      case _ => fail("Pixel at (1, 0) not loaded correctly.");
    }
    img.data.at(1, 1) match {
      case Some(RGBPixel(249, 166, 174)) =>
      case _ => fail("Pixel at (1, 1) not loaded correctly.");
    }
    img.data.at(1, 2) match {
      case Some(RGBPixel(255, 184, 13)) =>
      case _ => fail("Pixel at (1, 2) not loaded correctly.");
    }
    img.data.at(1, 3) match {
      case Some(RGBPixel(255, 157, 174)) =>
      case _ => fail("Pixel at (1, 3) not loaded correctly.");
    }
    img.data.at(1, 4) match {
      case Some(RGBPixel(0, 184, 240)) =>
      case _ => fail("Pixel at (1, 4) not loaded correctly.");
    }
    img.data.at(2, 0) match {
      case Some(RGBPixel(0, 177, 233)) =>
      case _ => fail("Pixel at (2, 0) not loaded correctly.");
    }
    img.data.at(2, 1) match {
      case Some(RGBPixel(4, 195, 245)) =>
      case _ => fail("Pixel at (2, 1) not loaded correctly.");
    }
    img.data.at(2, 2) match {
      case Some(RGBPixel(247, 175, 176)) =>
      case _ => fail("Pixel at (2, 2) not loaded correctly.");
    }
    img.data.at(2, 3) match {
      case Some(RGBPixel(0, 192, 253)) =>
      case _ => fail("Pixel at (2, 3) not loaded correctly.");
    }
    img.data.at(2, 4) match {
      case Some(RGBPixel(14, 181, 251)) =>
      case _ => fail("Pixel at (2, 4) not loaded correctly.");
    }
    img.data.at(3, 0) match {
      case Some(RGBPixel(5, 180, 231)) =>
      case _ => fail("Pixel at (3, 0) not loaded correctly.");
    }
    img.data.at(3, 1) match {
      case Some(RGBPixel(5, 179, 248)) =>
      case _ => fail("Pixel at (3, 1) not loaded correctly.");
    }
    img.data.at(3, 2) match {
      case Some(RGBPixel(160, 221, 29)) =>
      case _ => fail("Pixel at (3, 2) not loaded correctly.");
    }
    img.data.at(3, 3) match {
      case Some(RGBPixel(0, 186, 243)) =>
      case _ => fail("Pixel at (3, 3) not loaded correctly.");
    }
    img.data.at(3, 4) match {
      case Some(RGBPixel(175, 236, 19)) =>
      case _ => fail("Pixel at (3, 4) not loaded correctly.");
    }
    img.data.at(4, 0) match {
      case Some(RGBPixel(0, 190, 249)) =>
      case _ => fail("Pixel at (4, 0) not loaded correctly.");
    }
    img.data.at(4, 1) match {
      case Some(RGBPixel(19, 182, 241)) =>
      case _ => fail("Pixel at (4, 1) not loaded correctly.");
    }
    img.data.at(4, 2) match {
      case Some(RGBPixel(177, 226, 10)) =>
      case _ => fail("Pixel at (4, 2) not loaded correctly.");
    }
    img.data.at(4, 3) match {
      case Some(RGBPixel(172, 235, 34)) =>
      case _ => fail("Pixel at (4, 3) not loaded correctly.");
    }
    img.data.at(4, 4) match {
      case Some(RGBPixel(0, 174, 231)) =>
      case _ => fail("Pixel at (4, 4) not loaded correctly.");
    }
  }

  test("gif fails") {
    val path = s"${TestUtils.get_test_images_path()}/gif/red_pixel.gif"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image with an incorrect extension loaded succesfully.");
      case Right(_) =>
    };
  }

  test("png fails") {
    val path = s"${TestUtils.get_test_images_path()}/png/red_pixel.png"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image with an incorrect extension loaded succesfully.");
      case Right(_) =>
    };
  }

  test("folder fails") {
    val path = "./src"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a folder.");
      case Right(_) =>
    };
  }

  test("non-existent fails") {
    val path = "./src/this/file/does/not/exist.jpg"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a non-existent file.");
      case Right(_) =>
    };
  }

  test("non-image fails") {
    val path = "./README.md"
    val loader = new JPGLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a non-image.");
      case Right(_) =>
    };
  }
}
