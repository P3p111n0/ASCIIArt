package ui.args;

/**
 * A trait that represents an operation on grayscale values.
 *
 * @param iterable True if the argument can be used multiple times in a sequence, false otherwise.
 * @param name     The name of the argument.
 */
abstract class GrayscaleArg(iterable: Boolean, name: String) extends Arg(iterable = iterable, name = name);

object InvertArg extends GrayscaleArg(iterable = true, name = "invert");

case class BrightnessArg(val offset: Int) extends GrayscaleArg(iterable = false, name = "brightness");
