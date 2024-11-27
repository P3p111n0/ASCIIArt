package ui.parser;

import ui.args.Arg;
import error.Error;
import ui.parser.partial.{PartialParser, LoadArgParser, RotateArgParser, ScaleArgParser, FlipArgParser, InvertArgParser, BrightnessParser};

trait Parser {
  protected val parser_map : Map[String, PartialParser] = Map(
    "--image" -> LoadArgParser,
    "--image-random" -> LoadArgParser,
    "--rotate" -> RotateArgParser,
    "--scale" -> ScaleArgParser,
    "--flip" -> FlipArgParser,
    "--invert" -> InvertArgParser,
    "--brightness" -> BrightnessParser
    )
  def parse(): Either[Seq[Arg], Error];
}
