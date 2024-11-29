package ui.parser.partial;

import org.scalatest.funsuite.AnyFunSuite
import ui.parser.partial.ParserResult
import ui.args.{XFlipArg, YFlipArg}


class FlipArgParserTest extends AnyFunSuite {
    test("fails with empty args") {
        val result = FlipArgParser.parse(Seq[String]());
        assert(result.tokens.isEmpty);
        result.arg match {
            case Left(_) => fail("Parsed flip arg from empty args");
            case Right(e) => assert(e.msg == "Missing arguments for flip.");
        }
    }

    test("fails with invalid command") {
        val args = Seq[String]("invalid", "also invalid");
        val result = FlipArgParser.parse(args);
        assert(result.tokens == args);
        result.arg match {
            case Left(_) => fail("Parsed flip arg from invalid args");
            case Right(e) => assert(e.msg == "Invalid command.");
        }
    }

    test("fails with invalid flip axis") {
        val args = Seq[String]("--flip", "z", "extra");
        val result = FlipArgParser.parse(args);
        assert(result.tokens == Seq[String]("extra"));
        result.arg match {
            case Left(_) => fail("Parsed flip arg with invalid axis");
            case Right(e) => assert(e.msg == "Invalid flip axis.");
        }
    }

    test("parses xflip") {
        val args = Seq[String]("--flip", "x", "extra");
        val result = FlipArgParser.parse(args);
        assert(result.tokens == Seq[String]("extra"));
        result.arg match {
            case Left(XFlipArg) =>
            case _ => fail("Failed to parse x flip");
        }
    }

    test("parses yflip") {
        val args = Seq[String]("--flip", "y", "extra");
        val result = FlipArgParser.parse(args);
        assert(result.tokens == Seq[String]("extra"));
        result.arg match {
            case Left(YFlipArg) =>
            case _ => fail("Failed to parse y flip");
        }
    }
}
