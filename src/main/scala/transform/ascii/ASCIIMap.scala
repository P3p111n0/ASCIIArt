package transform.ascii;

import image.pixel.{Pixel, ASCIIPixel};

trait ASCIIMap[T](val map : String) {
  def apply(value : T): ASCIIPixel;
}

class ASCIIIntMap(val map_ : String) extends ASCIIMap[Int](map_) {
  override def apply(value: Int): ASCIIPixel = {
    val scaled = Math.min(Math.max(0, value), map.length() - 1);
    val c = map(scaled);
    ASCIIPixel(c) match {
      case Right(_) => return ASCIIPixel(); // this shouldnt happen;
      case Left(p) => return p;
    }
  }
}

object StandardASCIIMap extends ASCIIIntMap("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`\'. ");
