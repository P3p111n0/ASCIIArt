package ui.parser.partial

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{BuiltinTableArg, CustomTableArg}

class TableArgParserTest extends AnyFunSuite {
  test("fails with empty args") {
    val result = TableArgParser.parse(List());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Left(_) => fail("Parsed table arg from empty args.");
      case Right(e) => assert(e.msg == "Insufficient arguments.");
    }
  }

  test("fails with invalid args") {
    val args = Seq("--invalid", "invalid");
    val result = TableArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Left(_) => fail("Parsed table arg from invalid args.");
      case Right(e) => assert(e.msg == "Invalid command.");
    }
  }

  test("parses builtin table arg") {
    val args = Seq("--table", "name", "other_command");
    val result = TableArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(BuiltinTableArg("name")) =>
      case _ => fail("Failed to parse builtin table arg.");
    }
  }

  test("parses custom table arg") {
    val args = Seq("--custom-table", "some_map", "other_command");
    val result = TableArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(CustomTableArg("some_map")) =>
      case _ => fail("Failed to parse custom table arg.");
    }
  }
}
