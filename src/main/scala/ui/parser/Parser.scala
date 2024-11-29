package ui.parser;

import ui.args.Arg;
import error.Error;
import ui.parser.partial.*

trait Parser {
  protected val parser_map : Map[String, PartialParser] = Map(
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
  def parse(): Either[Seq[Arg], Error];
}
