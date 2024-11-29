package ui.args;

/**
 * A trait that represents an argument specifying an io operation.
 *
 * @param iterable True if the argument can be used multiple times in a sequence, false otherwise.
 * @param name     The name of the argument.
 */
abstract class IOArg(iterable: Boolean, name: String) extends Arg(iterable = iterable, name = name);
