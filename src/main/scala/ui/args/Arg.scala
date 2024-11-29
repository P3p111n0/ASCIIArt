package ui.args;

/**
 * A trait that represents a command line argument.
 *
 * @param iterable True if the argument can be used multiple times in a sequence, false otherwise.
 * @param name     The name of the argument.
 */
trait Arg(val iterable: Boolean, val name: String);
