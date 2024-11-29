package utils

import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils

import java.io.File

class FileUtilsTest extends AnyFunSuite {
  private val base = TestUtils.get_test_images_path();
  test("get extension - jpg") {
    val f = new java.io.File("test.jpg");
    FileUtils.get_file_ext(f) match {
      case Some(ext) => assert(ext == "jpg");
      case None => fail("Failed to get jpg extension.");
    }
  }

  test("get extension - no extension") {
    val f = new java.io.File("test");
    FileUtils.get_file_ext(f) match {
      case Some(_) => fail("Got extension for file without extension.");
      case None =>
    }
  }

  test("get extension - png") {
    val path = "test.png";
    FileUtils.get_file_ext(path) match {
      case Some(ext) => assert(ext == "png");
      case None => fail("Failed to get extension.");
    }
  }

  test("get extension - gif") {
    val f = new java.io.File("test.gif");
    FileUtils.get_file_ext(f) match {
      case Some(ext) => assert(ext == "gif");
      case None => fail("Failed to get extension.");
    }
  }

  test("get extension from string - png") {
    val path = "test.png";
    FileUtils.get_file_ext(path) match {
      case Some(ext) => assert(ext == "png");
      case None => fail("Failed to get extension.");
    }
  }

  test("get extension from string - gif") {
    val path = "test.gif";
    FileUtils.get_file_ext(path) match {
      case Some(ext) => assert(ext == "gif");
      case None => fail("Failed to get extension.");
    }
  }

  test("get extension from string - no extension") {
    val path = "test";
    FileUtils.get_file_ext(path) match {
      case Some(_) => fail("Got extension for file without extension.");
      case None =>
    }
  }

  test("get extension - txt") {
    val f = new java.io.File("test.txt");
    FileUtils.get_file_ext(f) match {
      case Some(ext) => assert(ext == "txt");
      case None => fail("Failed to get extension.");
    }
  }

  test("is image file - jpg image") {
    val path = base + "jpg/bobinka.jpg";
    val f = new File(path);
    assert(FileUtils.is_image_file(f));
  }

  test("is image file - gif image") {
    val path = base + "gif/bobinka.gif";
    val f = new File(path);
    assert(FileUtils.is_image_file(f));
  }

  test("is image file - png file") {
    val path = base + "png/bobinka.png";
    val f = new File(path);
    assert(FileUtils.is_image_file(f));
  }

  test("is image file - readme") {
    val path = "./README.md";
    val f = new File(path);
    assert(!FileUtils.is_image_file(f));
  }

  test("is image file - directory") {
    val path = "./src";
    val f = new File(path);
    assert(!FileUtils.is_image_file(f));
  }

  test("validate image file - jpg image") {
    val path = base + "jpg/bobinka.jpg";
    val f = new File(path);
    assert(FileUtils.validate_image_file(f).isEmpty);
  }

  test("validate image file - gif image") {
    val path = base + "gif/bobinka.gif";
    val f = new File(path);
    assert(FileUtils.validate_image_file(f).isEmpty);
  }

  test("validate image file - png image") {
    val path = base + "png/bobinka.png";
    val f = new File(path);
    assert(FileUtils.validate_image_file(f).isEmpty);
  }

  test("validate image file - readme") {
    val path = "./README.md";
    val f = new File(path);
    assert(FileUtils.validate_image_file(f).isDefined);
  }

  test("validate image file - directory") {
    val path = "./src";
    val f = new File(path);
    assert(FileUtils.validate_image_file(f).isDefined);
  }
}
