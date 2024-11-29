package io.saver

import image.Image
import image.data.ImageData
import image.pixel.ASCIIPixel
import org.scalatest.funsuite.AnyFunSuite
import testing.TestWithFiles
import java.io.File;

class TextFileSaverTest extends AnyFunSuite with TestWithFiles {
  test("single pixel") {
    val filename = getTestFile
    try {
      ensureDeleted(filename)
      val file = new File(filename)
      val pixel = ASCIIPixel('a') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(pixel))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      val saver = TextFileSaver(filename);

      saver.save(image) match {
        case Some(value) => fail(value.msg);
        case None => assertFileContent(filename, "a\n");
      }
    }
    finally {
      ensureDeleted(filename)
    }
  }

  test("two pixels in a row") {
    val filename = getTestFile
    try {
      ensureDeleted(filename)
      val file = new File(filename)
      val pixel1 = ASCIIPixel('a') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val pixel2 = ASCIIPixel('b') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(pixel1, pixel2))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      val saver = TextFileSaver(filename);

      saver.save(image) match {
        case Some(value) => fail(value.msg);
        case None => assertFileContent(filename, "ab\n");
      }
    }
    finally {
      ensureDeleted(filename)
    }
  }

  test("two pixels in a column") {
    val filename = getTestFile;
    try {
      ensureDeleted(filename);
      val file = new File(filename);
      val pixel1 = ASCIIPixel('a') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val pixel2 = ASCIIPixel('b') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(pixel1), Vector(pixel2))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      val saver = TextFileSaver(filename);

      saver.save(image) match {
        case Some(value) => fail(value.msg);
        case None => assertFileContent(filename, "a\nb\n");
      }
    } finally {
      ensureDeleted(filename)
    }
  }

  test("2x2 image") {
    val filename = getTestFile;
    try {
      ensureDeleted(filename);
      val file = new File(filename);
      val pixel1 = ASCIIPixel('a') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val pixel2 = ASCIIPixel('b') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val pixel3 = ASCIIPixel('c') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val pixel4 = ASCIIPixel('d') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(pixel1, pixel2), Vector(pixel3, pixel4))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      val saver = TextFileSaver(filename);

      saver.save(image) match {
        case Some(value) => fail(value.msg);
        case None => assertFileContent(filename, "ab\ncd\n");
      }
    } finally {
      ensureDeleted(filename)
    }
  }

  test("3x3 image") {
    val filename = getTestFile;
    try {
      ensureDeleted(filename);
      val file = new File(filename);
      val p1 = ASCIIPixel(' ') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val p2 = ASCIIPixel('x') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val p3 = ASCIIPixel('y') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(p1, p2, p1), Vector(p2, p3, p2), Vector(p1, p2, p1))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      val saver = TextFileSaver(filename);

      saver.save(image) match {
        case Some(value) => fail(value.msg);
        case None => assertFileContent(filename, " x \nxyx\n x \n");
      }
    } finally {
      ensureDeleted(filename)
    }
  }

  test("unwritable file") {
    val filename = getTestFile;
    try {
      ensureDeleted(filename);
      val file = new File(filename);
      val pixel = ASCIIPixel('a') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val data = ImageData(Vector(Vector(pixel))) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
      }
      val image = Image(data);
      assert(file.createNewFile());
      assert(file.setReadOnly());
      val saver = TextFileSaver(filename);
      saver.save(image) match {
        case Some(e) => assert(e.msg == "Cannot write to file: " + filename);
        case None => fail("Successfully wrote to a read only file.");
      }
    } finally {
      ensureDeleted(filename)
    }
  }
}
