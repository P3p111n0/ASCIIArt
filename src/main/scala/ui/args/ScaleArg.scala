package ui.args;

import error.Error;

class ScaleArg (val factor : Double) extends SingleParamArg[Double](param = factor, iterable = true);
