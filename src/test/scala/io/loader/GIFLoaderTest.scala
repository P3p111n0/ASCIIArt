package io.loader

import image.pixel.RGBPixel
import org.scalatest.funsuite.AnyFunSuite
import testing.{MockDecoder, TestUtils}

class GIFLoaderTest extends AnyFunSuite{
  private val base = TestUtils.get_test_images_path() + "gif";
  test("red pixel") {
    val path = s"$base/red_pixel.gif"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 1)
    assert(img.height() == 1)
    img.data.at(0, 0) match {
      case Some(RGBPixel(255, 0, 0)) =>
      case _ => fail("Red pixel not loaded correctly.");
    }
  }

  test("white pixel") {
    val path = s"$base/white_pixel.gif"
    val loader = new GIFLoader(path, MockDecoder);
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
    val path = s"$base/light_blue_pixel.gif"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 1)
    assert(img.height() == 1)
    img.data.at(0, 0) match {
      case Some(RGBPixel(85, 156, 227)) =>
      case _ => fail("Light blue pixel not loaded correctly.");
    }
  }

  test("colours") {
    val path = s"$base/colours.gif"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 3)
    assert(img.height() == 3)
    img.data.at(0, 0) match {
      case Some(RGBPixel(255, 0, 0)) =>
      case _ => fail("Red pixel not loaded correctly.");
    }
    img.data.at(0, 1) match {
      case Some(RGBPixel(0, 255, 0)) =>
      case _ => fail("Green pixel not loaded correctly.");
    }
    img.data.at(0, 2) match {
      case Some(RGBPixel(0, 0, 255)) =>
      case _ => fail("Blue pixel not loaded correctly.");
    }
    img.data.at(1, 0) match {
      case Some(RGBPixel(242, 217, 18)) =>
      case _ => fail("Yellow pixel not loaded correctly.");
    }
    img.data.at(1, 1) match {
      case Some(RGBPixel(18, 242, 186)) =>
      case _ => fail("Cyan pixel not loaded correctly.");
    }
    img.data.at(1, 2) match {
      case Some(RGBPixel(143, 18, 242)) =>
      case _ => fail("Purple pixel not loaded correctly.");
    }
    img.data.at(2, 0) match {
      case Some(RGBPixel(31, 187, 99)) =>
      case _ => fail("Dark green pixel not loaded correctly.");
    }
    img.data.at(2, 1) match {
      case Some(RGBPixel(255, 255, 255)) =>
      case _ => fail("White pixel not loaded correctly.");
    }
    img.data.at(2, 2) match {
      case Some(RGBPixel(0, 0, 0)) =>
      case _ => fail("Black pixel not loaded correctly.");
    }
  }

  test("bobinka") {
    val path = s"$base/bobinka.gif"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    };
    assert(img.width() == 5)
    assert(img.height() == 5)
    img.data.at(0, 0) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (0, 0) not loaded correctly.");
    }
    img.data.at(0, 1) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (0, 1) not loaded correctly.");
    }
    img.data.at(0, 2) match {
      case Some(RGBPixel(255, 163, 177)) =>
      case _ => fail("Pixel at (0, 2) not loaded correctly.");
    }
    img.data.at(0, 3) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (0, 3) not loaded correctly.");
    }
    img.data.at(0, 4) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (0, 4) not loaded correctly.");
    }
    img.data.at(1, 0) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (1, 0) not loaded correctly.");
    }
    img.data.at(1, 1) match {
      case Some(RGBPixel(255, 163, 177)) =>
      case _ => fail("Pixel at (1, 1) not loaded correctly.");
    }
    img.data.at(1, 2) match {
      case Some(RGBPixel(255, 194, 14)) =>
      case _ => fail("Pixel at (1, 2) not loaded correctly.");
    }
    img.data.at(1, 3) match {
      case Some(RGBPixel(255, 163, 177)) =>
      case _ => fail("Pixel at (1, 3) not loaded correctly.");
    }
    img.data.at(1, 4) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (1, 4) not loaded correctly.");
    }
    img.data.at(2, 0) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (2, 0) not loaded correctly.");
    }
    img.data.at(2, 1) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (2, 1) not loaded correctly.");
    }
    img.data.at(2, 2) match {
      case Some(RGBPixel(255, 163, 177)) =>
      case _ => fail("Pixel at (2, 2) not loaded correctly.");
    }
    img.data.at(2, 3) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (2, 3) not loaded correctly.");
    }
    img.data.at(2, 4) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (2, 4) not loaded correctly.");
    }
    img.data.at(3, 0) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (3, 0) not loaded correctly.");
    }
    img.data.at(3, 1) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (3, 1) not loaded correctly.");
    }
    img.data.at(3, 2) match {
      case Some(RGBPixel(168, 230, 29)) =>
      case _ => fail("Pixel at (3, 2) not loaded correctly.");
    }
    img.data.at(3, 3) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (3, 3) not loaded correctly.");
    }
    img.data.at(3, 4) match {
      case Some(RGBPixel(168, 230, 29)) =>
      case _ => fail("Pixel at (3, 4) not loaded correctly.");
    }
    img.data.at(4, 0) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (4, 0) not loaded correctly.");
    }
    img.data.at(4, 1) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (4, 1) not loaded correctly.");
    }
    img.data.at(4, 2) match {
      case Some(RGBPixel(168, 230, 29)) =>
      case _ => fail("Pixel at (4, 2) not loaded correctly.");
    }
    img.data.at(4, 3) match {
      case Some(RGBPixel(168, 230, 29)) =>
      case _ => fail("Pixel at (4, 3) not loaded correctly.");
    }
    img.data.at(4, 4) match {
      case Some(RGBPixel(0, 183, 239)) =>
      case _ => fail("Pixel at (4, 4) not loaded correctly.");
    }
  }

  test("png fails") {
    val path = s"${TestUtils.get_test_images_path()}/png/red_pixel.png"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image with an incorrect extension loaded succesfully.");
      case Right(_) =>
    };
  }

  test("jpg fails") {
    val path = s"${TestUtils.get_test_images_path()}/jpg/red_pixel.jpg"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image with an incorrect extension loaded succesfully.");
      case Right(_) =>
    };
  }

  test("folder fails") {
    val path = "./src"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a folder.");
      case Right(_) =>
    };
  }

  test("non-existent fails") {
    val path = "./src/this/file/does/not/exist.gif"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a non-existent file.");
      case Right(_) =>
    };
  }

  test("non-image fails") {
    val path = "./.gitignore"
    val loader = new GIFLoader(path, MockDecoder);
    val img = loader.load() match {
      case Left(value) => fail("Image loaded from a non-image.");
      case Right(_) =>
    };
  }
}
