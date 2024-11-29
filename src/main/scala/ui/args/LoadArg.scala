package ui.args;

/**
 * A trait that represents a command line argument.
 *
 * @param name The name of the argument.
 */
abstract class LoadArg(name: String) extends IOArg(iterable = false, name = name);

case class ImageArg(val path: String) extends LoadArg(name = "image");

object RandomImageArg extends LoadArg(name = "image-random");
