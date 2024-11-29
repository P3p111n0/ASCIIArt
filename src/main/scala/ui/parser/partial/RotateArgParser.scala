package ui.parser.partial

import error.Error;
import ui.args.RotateArg;

object RotateArgParser extends PartialParser {
  def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens; 
    if (new_tokens.length < 2) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    if (new_tokens.head != "--rotate") {
      return new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }

    new_tokens = new_tokens.drop(1);
    try {
      val angle = new_tokens.head.toInt;
      return new ParserResult(new_tokens.drop(1), Left(new RotateArg(angle)));
    } catch {
      case _ : Exception => new ParserResult(new_tokens.drop(1), Right(new Error("Couldn't parse rotation angle.")));
    }
  }
}
