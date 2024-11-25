package transform.ascii;

import image.pixel.{Pixel, ASCIIPixel};
import error.InternalException

trait ASCIIMap[T] {
  def apply(value : T): ASCIIPixel;
}

abstract class ASCIIStringMap[T](val map : String) extends ASCIIMap[T];

class ASCIIIntMap(val map_ : String) extends ASCIIStringMap[Int](map_) {
  private val max = 255;
  override def apply(value: Int): ASCIIPixel = {
    val ratio : Double = max / map_.length();
    var scaled = (value / ratio).toInt;
    scaled = Math.min(scaled, map_.length() - 1);
    if (scaled >= map_.length()) {
      throw new InternalException("ASCIIIntMap: scaled value %d is out of range.".format(scaled));
    }
    val c = map_(scaled);
    ASCIIPixel(c) match {
      case Right(_) => throw new InternalException("ASCIIIntMap: Couldn't construct ASCIIPixel.");
      case Left(p) => return p;
    }
  }
}

object StandardASCIIMap extends ASCIIIntMap(" .\'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$");
