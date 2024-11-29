package ui.parser.partial

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{ScaleArg}

class ScaleArgParserTest extends AnyFunSuite {
  test("fails with empty arguments") {
    val result = ScaleArgParser.parse(Seq[String]());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Right(e) => assert(e.msg == "Insufficient arguments.");
      case Left(_) => fail("Scale arg parsed from empty arguments.");
    }
  }

  test("fails with invalid arguments") {
    val args = Seq("--invalid", "invalid ig");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Right(e) => assert(e.msg == "Invalid argument.");
      case Left(_) => fail("Scale arg parsed from invalid arguments.");
    }
  }

  test("fails with missing scale") {
    val args = Seq("--scale");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Right(e) => assert(e.msg == "Insufficient arguments.");
      case Left(_) => fail("Scale arg parsed with missing scale.");
    }
  }

  test("fails with invalid scale") {
    val args = Seq("--scale", "invalid", "extra");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Right(e) => assert(e.msg == "Couldn't parse scale factor.");
      case Left(_) => fail("Scale arg parsed with invalid scale.");
    }
  }

  test("parses identity") {
    val args = Seq("--scale", "1", "extra");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(ScaleArg(1)) =>
      case _ => fail("Scale arg failed to parse identity scale.");
    }
  }

  test("parses quad scale") {
    val args = Seq("--scale", "4", "extra");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(ScaleArg(4)) =>
      case _ => fail("Scale arg failed to parse quad scale.");
    }
  }

  test("parses any scale") {
    val args = Seq("--scale", "-2", "extra");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(ScaleArg(-2)) =>
      case _ => fail("Scale arg failed to parse negative scale.");
    }
  }

  test("parses quarter scale") {
    val args = Seq("--scale", "0.25", "extra");
    val result = ScaleArgParser.parse(args);
    assert(result.tokens == args.drop(2));
    result.arg match {
      case Left(ScaleArg(0.25)) =>
      case _ => fail("Scale arg failed to parse float scale.");
    }
  }
}
