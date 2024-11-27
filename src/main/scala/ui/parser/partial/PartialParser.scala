package ui.parser.partial;

import error.Error;
import ui.args.Arg;

case class ParserResult(val tokens : Seq[String], val arg : Either[Arg, Error]);

trait PartialParser {
  def parse(tokens : Seq[String]): ParserResult; 
}
