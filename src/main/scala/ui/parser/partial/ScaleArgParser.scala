package ui.parser.partial

import error.Error;
import ui.args.ScaleArg;

object ScaleArgParser extends PartialParser {
  def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens; 
    if (new_tokens.length < 2) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    if (new_tokens.head != "--scale") {
      return new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }

    new_tokens = new_tokens.drop(1);
    try {
      val factor = new_tokens.head.toDouble;
      return new ParserResult(new_tokens.drop(1), Left(new ScaleArg(factor)));
    } catch {
      case _ : Exception => new ParserResult(new_tokens.drop(1), Right(new Error("Couldn't parse scale factor.")));
    }
  }
}
