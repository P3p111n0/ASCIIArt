package ui.args;

abstract class LoadArg extends Arg(iterable = false); 

class ImageArg(private val path : String) extends LoadArg;

object RandomImageArg extends LoadArg;
