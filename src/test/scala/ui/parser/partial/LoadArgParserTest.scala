package ui.parser.partial

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{ImageArg, RandomImageArg}

class LoadArgParserTest extends AnyFunSuite {
  test("fails with empty arguments") {
    val result = LoadArgParser.parse(Seq[String]());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Right(e) => assert(e.msg == "Insufficient arguments.");
      case Left(_) => fail("Load arg parsed from empty arguments.");
    }
  }

  test("fails with invalid arguments") {
    val args = Seq("--invalid", "invalid ig");
    val result = LoadArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Right(e) => assert(e.msg == "Invalid argument.");
      case Left(_) => fail("Load arg parsed from invalid arguments.");
    }
  }

  test("fails with missing path") {
    val args = Seq("--image");
    val result = LoadArgParser.parse(args);
    assert(result.tokens.isEmpty);
    result.arg match {
      case Right(e) => assert(e.msg == "Missing image path.");
      case Left(_) => fail("Load arg parsed with missing path.");
    }
  }

  test("parses image path") {
    val args = Seq("--image", "path/to/image", "extra");
    val result = LoadArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(ImageArg("path/to/image")) =>
      case _ => fail("Failed to parse path image arg.");
    }
  }

  test("parses random image") {
    val args = Seq("--image-random", "extra");
    val result = LoadArgParser.parse(args);
    assert(result.tokens == args.drop(1));
    result.arg match {
      case Left(RandomImageArg) =>
      case _ => fail("Failed to parse random image arg.");
    }
  }
}
