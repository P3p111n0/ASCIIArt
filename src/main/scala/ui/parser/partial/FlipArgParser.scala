package ui.parser.partial

import error.Error
import ui.args.{XFlipArg, YFlipArg}

object FlipArgParser extends PartialParser {
  def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 2) {
      return new ParserResult(new_tokens, Right(new Error("Missing arguments for flip.")));
    }
    if (new_tokens(0) != "--flip") {
      return new ParserResult(new_tokens, Right(new Error("Invalid command.")));
    }

    new_tokens = new_tokens.drop(1);
    new_tokens.head match {
        case "x" => new ParserResult(new_tokens.drop(1), Left(XFlipArg));
        case "y" => new ParserResult(new_tokens.drop(1), Left(YFlipArg));
        case _ => new ParserResult(new_tokens.drop(1), Right(new Error("Invalid flip axis.")));
    }
  }
}
