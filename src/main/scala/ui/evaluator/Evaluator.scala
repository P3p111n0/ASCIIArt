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

/**
 * A trait that represents an evaluator for program arguments.
 */
trait Evaluator(private val args: Seq[Arg]) {
  /**
   * Evaluates arguments and returns an image loader.
   *
   * @return An image loader or an error, if multiple loaders are specified.
   */
  def get_loader(): Either[Loader[RGBPixel], Error];

  /**
   * Evaluates arguments and returns an encoder.
   *
   * @return An encoder or an error, if some encoders aren't allowed to be used multiple times.
   */
  def get_encoder(): Either[Encoder[RGBPixel, Int], Error];

  /**
   * Evaluates arguments and returns an ASCII map.
   *
   * @return An ASCII map or an error, if multiple ASCII maps are specified.
   */
  def get_ascii_map(): Either[ASCIIMap[Int], Error];

  /**
   * Evaluates arguments and returns a sequence of transformations.
   *
   * @return A sequence of transformations or an error, if some transformations aren't allowed to be
   *         used multiple times or if a transformation is cannot be constructed from provided arguments.
   */
  def get_filters(): Either[Seq[Transformation[ASCIIPixel, ASCIIPixel]], Error];

  /**
   * Evaluates arguments and returns a sequence of exporters.
   *
   * @return A sequence of exporters or an error, if some exporters aren't allowed to be used multiple
   *         times or if an exporter is cannot be constructed from provided arguments.
   */
  def get_exporters(): Either[Seq[Saver[ASCIIPixel]], Error];
}
