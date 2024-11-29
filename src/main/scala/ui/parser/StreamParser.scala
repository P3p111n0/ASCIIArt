package ui.parser;

import ui.parser.Parser;
import java.io.InputStream;
import ui.args.Arg;
import error.Error;
import java.util.Scanner

class StreamParser(private val in : InputStream) extends Parser {
  private val delimiter = " ";
  override def parse(): Either[Seq[Arg], error.Error] = {
    var result = Seq[Arg]();
    val scanner = new Scanner(in).useDelimiter(delimiter);
    
    var tokens = Seq[String]();
    while (scanner.hasNext()) {
      tokens = tokens.appended(scanner.next()); 
    }
    
    while (tokens.length != 0) {
      val head = tokens(0);
      if (!parser_map.isDefinedAt(head)) {
        return Right(new Error(s"Invalid argument $head."));
      }
      val parser = parser_map(head);
      val parsing_result = parser.parse(tokens);
      
      parsing_result.arg match {
        case Left(argument) => {
          result = result.appended(argument);
          tokens = parsing_result.tokens;
        }
        case Right(e) => return Right(e);
      }
    }
    
    return Left(result);
  }
}
