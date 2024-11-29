package ui.args;

/**
 * A trait that represents an argument specifying an export location.
 *
 * @param iterable True if the argument can be used multiple times in a sequence, false otherwise.
 * @param name     The name of the argument.
 */
abstract class ExportArg(iterable: Boolean, name: String) extends IOArg(iterable = iterable, name = name);

case class FileExportArg(val path: String) extends ExportArg(iterable = false, name = "export-file");

object ConsoleExportArg extends ExportArg(iterable = false, name = "export-console");
