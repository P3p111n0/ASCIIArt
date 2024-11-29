package ui.parser.partial;

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{BrightnessArg}


class BrightnessParserTest extends AnyFunSuite {
  test("fails with empty args") {
    val result = BrightnessParser.parse(Seq[String]());
    assert(result.tokens.isEmpty);
    result.arg match {
      case Left(_) => fail("Parsed invert arg from empty args");
      case Right(e) => assert(e.msg == "Insufficient number of arguments.");
    }
  }

  test("fails with invalid args") {
    val args = Seq[String]("invalid", "also invalid");
    val result = BrightnessParser.parse(args);
    assert(result.tokens == args);
    result.arg match {
      case Left(_) => fail("Parsed invert arg from invalid args");
      case Right(e) => assert(e.msg == "Invalid argument.");
    }
  }

  test("fails with invalid brightness offset") {
    val args = Seq[String]("--brightness", "invalid");
    val result = BrightnessParser.parse(args);
    assert(result.tokens == Seq[String]());
    result.arg match {
      case Left(_) => fail("Parsed brightness arg from invalid args");
      case Right(e) => assert(e.msg == "Couldn't parse brightness offset.");
    }
  }

  test("fails with float brightness") {
    val args = Seq[String]("--brightness", "0.128", "extra");
    val result = BrightnessParser.parse(args);
    assert(result.tokens == Seq[String]("extra"));
    result.arg match {
      case Left(_) => fail("Parsed brightness arg with float offset");
      case Right(e) => assert(e.msg == "Couldn't parse brightness offset.");
    }
  }

  test("parses brightness") {
    val args = Seq[String]("--brightness", "128", "extra");
    val result = BrightnessParser.parse(args);
    assert(result.tokens == Seq[String]("extra"));
    result.arg match {
      case Left(BrightnessArg(128)) =>
      case _ => fail("Failed to parse brightness");
    }
  }
}
