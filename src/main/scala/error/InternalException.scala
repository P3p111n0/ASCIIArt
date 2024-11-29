package error;

// class representing internal exceptions meant to crash the application
// used when invariants are violated
case class InternalException(msg: String) extends Exception(msg);
