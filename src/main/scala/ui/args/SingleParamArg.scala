package ui.args;

abstract class SingleParamArg[T](val param : T, val iterable : Boolean) extends Arg(iterable = iterable);
