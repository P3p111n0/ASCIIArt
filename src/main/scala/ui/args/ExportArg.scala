package ui.args;

abstract class ExportArg(iterable : Boolean, name : String) extends IOArg(iterable = iterable, name = name);

case class FileExportArg(val path : String) extends ExportArg(iterable = false, name = "export-file");

object ConsoleExportArg extends ExportArg(iterable = false, name = "export-console");
