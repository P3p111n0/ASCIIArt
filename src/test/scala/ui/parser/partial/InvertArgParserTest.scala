package ui.parser.partial;

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{InvertArg}


class InvertArgParserTest extends AnyFunSuite {
  test("fails with empty args") {
    val result = InvertArgParser.parse(Seq[String]());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Left(_) => fail("Parsed invert arg from empty args");
      case Right(e) => assert(e.msg == "Insufficient arguments.");
    }
  }

  test("fails with invalid args") {
    val args = Seq[String]("invalid", "also invalid");
    val result = InvertArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Left(_) => fail("Parsed invert arg from invalid args");
      case Right(e) => assert(e.msg == "Invalid argument.");
    }
  }

  test("parses invert") {
    val args = Seq[String]("--invert", "extra");
    val result = InvertArgParser.parse(args);
    assert(result.tokens == Seq[String]("extra"));
    result.arg match {
      case Left(InvertArg) =>
      case _ => fail("Failed to parse invert");
    }
  }
}
