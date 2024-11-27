package ui.args;

import error.Error;

class RotateArg(val angle: Int) extends SingleParamArg[Int](param = angle, iterable = true);
