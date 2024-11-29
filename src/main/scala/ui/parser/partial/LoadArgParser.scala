package ui.parser.partial

import error.Error
import ui.args.{ImageArg, RandomImageArg}

object LoadArgParser extends PartialParser {
  def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 1) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    new_tokens.head match {
      case "--image" => {
        new_tokens = new_tokens.drop(1);
        new_tokens.length match {
          case 0 => return new ParserResult(new_tokens, Right(new Error("Missing image path.")));
          case _ => {
            val path = new_tokens(0);
            return new ParserResult(new_tokens.drop(1), Left(new ImageArg(path)));
          } 
        }
      } 
      case "--image-random" => return new ParserResult(new_tokens.drop(1), Left(RandomImageArg)); 
      case _ => return new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }
  }
}
