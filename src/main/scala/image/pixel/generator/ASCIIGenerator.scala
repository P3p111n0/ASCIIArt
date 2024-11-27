package image.pixel.generator;

import image.pixel.ASCIIPixel;

class ASCIIGenerator(private val seed : Int) extends PseudoRandomGen[ASCIIPixel](seed) {
  override def next(): ASCIIPixel = {
    val c = gen.nextPrintableChar();
    return new ASCIIPixel(c);
  }
}
