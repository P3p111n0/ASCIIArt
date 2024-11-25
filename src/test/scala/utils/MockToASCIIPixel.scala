package utils;

import image.pixel.encoding.Encoder;
import transform.ascii.ASCIIMap;
import image.pixel.ASCIIPixel;

object MockToASCIIPixel extends ASCIIMap[Int] {
  override def apply(elem: Int): ASCIIPixel = {
    elem match {
      case 0 => ASCIIPixel(' ') match {
        case Left(value)  => value;
        case Right(value) => assert(false);
      };
      case 255 => ASCIIPixel('$') match {
        case Left(value)  => value;
        case Right(value) => assert(false);
      };
      case 89 => ASCIIPixel('/') match {
        case Left(value)  => value;
        case Right(value) => assert(false);
      };
      case _ => assert(false);
    }
  }
}
