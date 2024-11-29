package ui.parser;

import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayInputStream
import ui.args.*

class StreamParserTest extends AnyFunSuite {
  private def get_stream(s : String): ByteArrayInputStream = {
    return new ByteArrayInputStream(s.getBytes());
  }

  test("parses empty args") {
    val stream = get_stream("");
    val parser = new StreamParser(stream)
    val result = parser.parse() match {
      case Left(a) => a;
      case Right(e) => fail(e.msg);
    }
    assert(result.isEmpty);
  }

  test("parses single arg") {
    val stream = get_stream("--invert");
    val parser = new StreamParser(stream);
    val result = parser.parse() match {
      case Left(a) => a;
      case Right(e) => fail(e.msg);
    }
    assert(result == Seq(InvertArg));
  }

  test("parses multiple args") {
    val stream = get_stream("--invert --brightness 10 --image path/to/image --image-random");
    val parser = new StreamParser(stream);
    val result = parser.parse() match {
      case Left(a) => a;
      case Right(e) => fail(e.msg);
    }
    assert(result == Seq(InvertArg, BrightnessArg(10), ImageArg("path/to/image"), RandomImageArg));
  }

  test("fails with invalid arg") {
    val stream = get_stream("--image path --invalid --rotate 90");
    val parser = new StreamParser(stream);
    val result = parser.parse() match {
      case Left(_) => fail("Parsed invalid args.");
      case Right(e) => assert(e.msg == "Invalid argument --invalid.");
    }
  }

  test("parses all args") {
    val stream = get_stream("--invert --brightness 10 --image path/to/image --image-random --rotate 90 " +
      "--flip x --flip y --table some_table --custom-table hsdafkjhasdfjh --scale 0.25 " +
      "--output-file some_file --output-console");
    val parser = new StreamParser(stream);
    val result = parser.parse() match {
      case Left(a) => a;
      case Right(e) => fail(e.msg);
    }
    assert(result == Seq(
      InvertArg, BrightnessArg(10), ImageArg("path/to/image"), RandomImageArg, RotateArg(90),
      XFlipArg, YFlipArg, BuiltinTableArg("some_table"), CustomTableArg("hsdafkjhasdfjh"),
      ScaleArg(0.25), FileExportArg("some_file"), ConsoleExportArg
    ));
  }

  test("parses all args 2") {
    val stream = get_stream("--invert --image path/to/image --image-random --rotate -56 " +
      "--flip x --flip y --table some_table --custom-table AHOOOOJ --scale 0.61 " +
      "--output-file some_file --output-console --invert --brightness 10 --image path2 --brightness -15");
    val parser = new StreamParser(stream);
    val result = parser.parse() match {
      case Left(a) => a;
      case Right(e) => fail(e.msg);
    }
    assert(result == Seq(
      InvertArg, ImageArg("path/to/image"), RandomImageArg, RotateArg(-56),
      XFlipArg, YFlipArg, BuiltinTableArg("some_table"), CustomTableArg("AHOOOOJ"),
      ScaleArg(0.61), FileExportArg("some_file"), ConsoleExportArg,
      InvertArg, BrightnessArg(10), ImageArg("path2"), BrightnessArg(-15)
    ));
  }
}