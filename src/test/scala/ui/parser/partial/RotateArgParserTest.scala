package ui.parser.partial

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{RotateArg}

class RotateArgParserTest extends AnyFunSuite {
  test("fails with empty arguments") {
    val result = RotateArgParser.parse(Seq[String]());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Right(e) => assert(e.msg == "Insufficient arguments.");
      case Left(_) => fail("Rotate arg parsed from empty arguments.");
    }
  }

  test("fails with invalid arguments") {
    val args = Seq("--invalid", "invalid ig");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Right(e) => assert(e.msg == "Invalid argument.");
      case Left(_) => fail("Rotate arg parsed from invalid arguments.");
    }
  }

  test("fails with missing angle") {
    val args = Seq("--rotate");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Right(e) => assert(e.msg == "Insufficient arguments.");
      case Left(_) => fail("Rotate arg parsed with missing angle.");
    }
  }

  test("fails with invalid angle") {
    val args = Seq("--rotate", "invalid", "extra");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Right(e) => assert(e.msg == "Couldn't parse rotation angle.");
      case Left(_) => fail("Rotate arg parsed with invalid angle.");
    }
  }

  test("fails with float angle") {
    val args = Seq("--rotate", "0.576", "extra");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Right(e) => assert(e.msg == "Couldn't parse rotation angle.");
      case Left(_) => fail("Rotate arg parsed with float angle.");
    }
  }

  test("parses positive angle") {
    val args = Seq("--rotate", "90", "extra");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(RotateArg(90)) =>
      case _ => fail("Failed to parse angle rotate arg.");
    }
  }

  test("parses negative angle") {
    val args = Seq("--rotate", "-90", "extra");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(RotateArg(-90)) =>
      case _ => fail("Failed to parse angle rotate arg.");
    }
  }

  test("parses unusable angle") {
    val args = Seq("--rotate", "15", "extra");
    val result = RotateArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(RotateArg(15)) =>
      case _ => fail("Failed to parse angle rotate arg.");
    }
  }
}
