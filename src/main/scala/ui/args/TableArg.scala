package ui.args;

abstract class TableArg(name : String) extends Arg(iterable = false, name = name);

case class BuiltinTableArg(val table_name : String) extends TableArg(name = "table");

case class CustomTableArg(val map : String) extends TableArg(name = "custom_table");
