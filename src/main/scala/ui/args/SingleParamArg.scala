package ui.args;

abstract class SingleParamArg[T](val param : T, iterable : Boolean, name : String) extends Arg(iterable = iterable, name = name);
