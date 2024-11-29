package ui.args;

import error.Error;

case class RotateArg(val angle: Int) extends SingleParamArg[Int](param = angle, iterable = true, name = "rotate");
