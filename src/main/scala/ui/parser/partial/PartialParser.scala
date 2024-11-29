package ui.parser.partial;

import error.Error;
import ui.args.Arg;

/**
 * A class that represents a result of a partial parser.
 *
 * @param tokens Tokens left after parsing.
 * @param arg    An argument or an error, if the argument couldn't be parsed.
 */
case class ParserResult(val tokens: Seq[String], val arg: Either[Arg, Error]);

/**
 * A trait that represents a partial parser for a single argument or argument group.
 */
trait PartialParser {
  /**
   * Parses tokens and returns a result of parsing.
   *
   * @param tokens Tokens to parse.
   * @return A parsing result.
   */
  def parse(tokens: Seq[String]): ParserResult;
}
