package ui.evaluator;

import ui.args.Arg
import loader.Loader
import image.pixel.RGBPixel
import error.Error
import image.pixel.encoding.Encoder
import image.pixel.ASCIIPixel
import transform.ascii.ASCIIMap
import transform.Transformation
import io.saver.Saver

trait Evaluator(private val args : Seq[Arg]) {
  def get_loader(): Either[Loader[RGBPixel], Error];
  def get_encoder(): Either[Encoder[RGBPixel, Int], Error];
  def get_ascii_map(): Either[ASCIIMap[Int], Error];
  def get_filters(): Either[Seq[Transformation[ASCIIPixel, ASCIIPixel]], Error];
  def get_exporters(): Either[Seq[Saver[ASCIIPixel]], Error];
}
