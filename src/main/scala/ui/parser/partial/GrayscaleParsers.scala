package ui.parser.partial;

import ui.args.{BrightnessArg, InvertArg};
import error.Error;

object InvertArgParser extends PartialParser {
  override def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 1) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    new_tokens.head match {
      case "--invert" => new ParserResult(new_tokens.drop(1), Left(InvertArg));
      case _ => new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }
  }
}

object BrightnessParser extends PartialParser {
  override def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 2) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient number of arguments.")));
    }

    if (new_tokens.head != "--brightness") {
      return new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }
    new_tokens = new_tokens.drop(1);
    try {
      val offset = new_tokens.head.toInt;
      return new ParserResult(new_tokens.drop(1), Left(new BrightnessArg(offset)));
    } catch {
      case _ : Exception => return new ParserResult(new_tokens.drop(1), Right(new Error("Couldn't parse brightness offset.")));
    }
  }
} 
