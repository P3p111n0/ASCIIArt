package ui.parser.partial

import error.Error
import ui.args.{FileExportArg, ConsoleExportArg}

object ExportArgParser extends PartialParser {
  def parse(tokens: Seq[String]): ParserResult = {
    var new_tokens = tokens;
    if (new_tokens.length < 1) {
      return new ParserResult(new_tokens, Right(new Error("Insufficient arguments.")));
    }

    new_tokens.head match {
      case "--output-file" => {
        new_tokens = new_tokens.drop(1);
        new_tokens.length match {
          case 0 => return new ParserResult(new_tokens, Right(new Error("Missing export file path.")));
          case _ => {
            val path = new_tokens(0);
            return new ParserResult(new_tokens.drop(1), Left(new FileExportArg(path)));
          } 
        }
      } 
      case "--output-console" => return new ParserResult(new_tokens.drop(1), Left(ConsoleExportArg)); 
      case _ => return new ParserResult(new_tokens, Right(new Error("Invalid argument.")));
    }
  }
}
