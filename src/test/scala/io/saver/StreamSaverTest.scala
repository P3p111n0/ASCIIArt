package io.saver

import image.Image
import image.data.ImageData
import image.pixel.ASCIIPixel
import org.scalatest.funsuite.AnyFunSuite
import error.Error
import io.saver.Saver
import testing.TestWithFiles

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, FileOutputStream, OutputStream}

object FakeSaver extends StreamSaver {
  def write(img: Image[ASCIIPixel], stream: OutputStream): Option[Error] = {
    return write_to_stream(img, stream);
  }

  override def save(img: Image[ASCIIPixel]): Option[Error] = {
    throw Exception("");
  }
}

class StreamSaverTest extends AnyFunSuite with TestWithFiles {
  test("single pixel") {
    val pixel = ASCIIPixel('a') match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val data = ImageData(Vector(Vector(pixel))) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val image = Image(data);
    val stream = new ByteArrayOutputStream();
    val saver = FakeSaver;

    val ret = saver.write(image, stream);
    assert(stream.toString() == "a\n");
  }

  test("two pixels in a row") {
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
    val stream = new ByteArrayOutputStream();
    val saver = FakeSaver;

    val ret = saver.write(image, stream);
    assert(stream.toString() == "ab\n");
  }

  test("two pixels in a column") {
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
    val stream = new ByteArrayOutputStream();
    val saver = FakeSaver;

    val ret = saver.write(image, stream);
    assert(stream.toString() == "a\nb\n");
  }

  test("2x2 image") {
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
    val stream = new ByteArrayOutputStream();
    val saver = FakeSaver;

    val ret = saver.write(image, stream);
    assert(stream.toString() == "ab\ncd\n");
  }

  test("3x3 image") {
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
    val stream = new ByteArrayOutputStream();
    val saver = FakeSaver;

    val ret = saver.write(image, stream);
    assert(stream.toString() == " x \nxyx\n x \n");
  }

  test("closed stream") {
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
      val stream = new FileOutputStream(file);
      stream.close();
      val saver = FakeSaver;

      val ret = saver.write(image, stream) match {
        case Some(value) => assert(value.msg == "java.io.IOException: Stream Closed");
        case None => fail("Successful write to a closed stream.");
      }

    }
    finally {
      ensureDeleted(filename)
    }
  }
}
