package transform.ascii;

import image.pixel.{Pixel, ASCIIPixel};
import error.InternalException

/**
 * A map from a value of type T to an ASCII pixel.
 *
 * @tparam T Target pixel type.
 */
trait ASCIIMap[T] {
  /**
   * Maps a value of type T to an ASCII pixel.
   *
   * @param value Value to transform
   * @return Resulting pixel
   */
  def apply(value: T): ASCIIPixel;
}

/**
 * A map from an integer to an ASCII pixel.
 *
 * @param map String to map to
 */
abstract class ASCIIStringMap[T](val map: String) extends ASCIIMap[T];

case class ASCIIIntMap(map_ : String) extends ASCIIStringMap[Int](map_) {
  private val max = 255;

  override def apply(value: Int): ASCIIPixel = {
    val ratio: Double = max / map_.length();
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

case class NonlinearASCIIMap(map_ : String) extends ASCIIStringMap[Int](map = map_) {
  override def apply(value: Int): ASCIIPixel = {
    val first_n_values = 255 - map.length();
    if (value < first_n_values) {
      return ASCIIPixel(map(0)) match {
        case Left(value) => value;
        case Right(_) => throw new InternalException("NonlinearASCIIMap: Couldn't construct ASCIIPixel");
      }
    }
    val new_value = value - first_n_values;
    return ASCIIPixel(map(new_value)) match {
      case Right(_) => throw new InternalException("ASCIIIntMap: Couldn't construct ASCIIPixel.");
      case Left(p) => return p;
    }
  }
}

object StandardASCIIMap extends ASCIIIntMap(" .\'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$");
