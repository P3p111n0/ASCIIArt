package ui.parser;

import ui.args.Arg;
import error.Error;
import ui.parser.partial.*

/**
 * A trait that represents a parser for command line arguments.
 */
trait Parser {
  /**
   * A map that maps arguments to their parsers.
   */
  protected val parser_map: Map[String, PartialParser] = Map(
    "--image" -> LoadArgParser,
    "--image-random" -> LoadArgParser,
    "--rotate" -> RotateArgParser,
    "--scale" -> ScaleArgParser,
    "--flip" -> FlipArgParser,
    "--invert" -> InvertArgParser,
    "--brightness" -> BrightnessParser,
    "--output-file" -> ExportArgParser,
    "--output-console" -> ExportArgParser,
    "--custom-table" -> TableArgParser,
    "--table" -> TableArgParser,
  )

  /**
   * Parses command line arguments.
   *
   * @return A sequence of arguments or an error, if some arguments are invalid.
   */
  def parse(): Either[Seq[Arg], Error];
}
