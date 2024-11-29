package ui.parser.partial

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{FileExportArg, ConsoleExportArg}


class ExportArgParserTest extends AnyFunSuite {
  test("fails with empty args") {
    val args = Seq[String]();
    val result = ExportArgParser.parse(args);
    assert(result.tokens.isEmpty);
    result.arg match {
      case Left(_) => fail("Arg parsed from empty args.");
      case Right(e) => assert(e.msg == "Insufficient arguments.");
    }
  }

  test("fails with invalid args") {
    val args = Seq[String]("x", "y", "z");
    val result = ExportArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Left(_) => fail("Arg parsed from invalid args.");
      case Right(e) => assert(e.msg == "Invalid argument.");
    }
  }

  test("fails with missing export file path") {
    val args = Seq[String]("--output-file");
    val result = ExportArgParser.parse(args);
    assert(result.tokens.isEmpty);
    result.arg match {
      case Left(_) => fail("Arg parsed with missing export file path.");
      case Right(e) => assert(e.msg == "Missing export file path.");
    }
  }

  test("parses file export arg") {
    val args = Seq[String]("--output-file", "path/to/file", "other_args");
    val result = ExportArgParser.parse(args);
    assert(result.tokens == Seq("other_args"));
    result.arg match {
      case Left(FileExportArg("path/to/file")) =>
      case _ => fail("Error parsing file export arg.");
    }
  }

  test("parses console export arg") {
    val args = Seq[String]("--output-console", "other_args");
    val result = ExportArgParser.parse(args);
    assert(result.tokens == Seq("other_args"));
    result.arg match {
      case Left(ConsoleExportArg) =>
      case _ => fail("Error parsing console export arg.");
    }
  }
}
