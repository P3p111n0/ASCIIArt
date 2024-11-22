package error;

case class InternalException(msg : String) extends Exception(msg);
