package ui.args;

abstract class GrayscaleArg(iterable : Boolean) extends Arg(iterable = iterable); 

object InvertArg extends GrayscaleArg(iterable = true);

class BrightnessArg(val offset : Int) extends GrayscaleArg(iterable = false);
