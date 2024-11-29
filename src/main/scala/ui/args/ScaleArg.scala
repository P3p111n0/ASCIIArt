package ui.args;

import error.Error;

case class ScaleArg (val factor : Double) extends SingleParamArg[Double](param = factor, iterable = true, name = "scale");
