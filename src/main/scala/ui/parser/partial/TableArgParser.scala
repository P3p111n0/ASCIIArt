package ui.parser.partial

import ui.args.{BuiltinTableArg, CustomTableArg}
import error.Error

object TableArgParser extends PartialParser {
  override def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 2) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    new_tokens.head match {
        case "--table" => {
            new_tokens = new_tokens.drop(1);
            val name = new_tokens.head;
            return new ParserResult(new_tokens.drop(1), Left(BuiltinTableArg(name)));
        }
        case "--custom-table" => {
          new_tokens = new_tokens.drop(1);
          val map = new_tokens.head;
          return new ParserResult(new_tokens.drop(1), Left(new CustomTableArg(map)));
        }
        case _ => return new ParserResult(new_tokens, Right(new Error("Invalid command.")));
    }
  }
}
