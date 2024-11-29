package Main

import app.ConsoleApp;

object Main {
  def main(console_args : Array[String]): Unit = {
    val app = new ConsoleApp(console_args); 
    app.run() match {
      case None => return;
      case Some(e) => {
        println(e.msg);
        return;
      }
    }
  }
}
