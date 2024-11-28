package ui.args;

abstract class GrayscaleArg(iterable : Boolean, name : String) extends Arg(iterable = iterable, name = name); 

object InvertArg extends GrayscaleArg(iterable = true, name = "invert");

case class BrightnessArg(val offset : Int) extends GrayscaleArg(iterable = false, name = "brightness");
