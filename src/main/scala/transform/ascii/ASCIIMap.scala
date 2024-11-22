package transform.ascii;

import image.pixel.{Pixel, ASCIIPixel};
import error.InternalException

trait ASCIIMap[T] {
  def apply(value : T): ASCIIPixel;
}

trait ASCIIStringMap[T](val map : String) extends ASCIIMap[T];

class ASCIIDoubleMap(val map_ : String) extends ASCIIStringMap[Double](map_) {
  override def apply(value: Double): ASCIIPixel = {
    val scaled = Math.round(value / map_.length()).toInt;
    if (scaled >= map_.length()) {
      throw new InternalException("ASCIIDoubleMap: scaled value %lf is out of range.".format(scaled));
    }
    val c = map_(scaled);
    ASCIIPixel(c) match {
      case Right(_) => return ASCIIPixel(); // this shouldnt happen;
      case Left(p) => return p;
    }
  }
}

object StandardASCIIMap extends ASCIIDoubleMap("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`\'. ");
