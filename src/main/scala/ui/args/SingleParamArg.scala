package ui.args;

/**
 * A trait that represents a command line argument with a single parameter.
 *
 * @param param    The parameter of the argument.
 * @param iterable True if the argument can be used multiple times in a sequence, false otherwise.
 * @param name     The name of the argument.
 * @tparam T Parameter type.
 */
abstract class SingleParamArg[T](val param: T, iterable: Boolean, name: String) extends Arg(iterable = iterable, name = name);
