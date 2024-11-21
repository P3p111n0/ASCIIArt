package transform.ascii;

import image.pixel.{Pixel, ASCIIPixel};

trait ASCIIMap[T](val map : String) {
  def apply(value : T): ASCIIPixel;
}

class ASCIIIntMap(val map_ : String) extends ASCIIMap[Double](map_) {
  override def apply(value: Double): ASCIIPixel = {
    val scaled = Math.round(value / map_.length()).toInt;
    assert(scaled < map_.length());
    val c = map_(scaled);
    ASCIIPixel(c) match {
      case Right(_) => return ASCIIPixel(); // this shouldnt happen;
      case Left(p) => return p;
    }
  }
}

object StandardASCIIMap extends ASCIIIntMap("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`\'. ");
