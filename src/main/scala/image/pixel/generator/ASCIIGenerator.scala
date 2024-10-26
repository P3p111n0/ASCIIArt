package image.pixel.generator;

import image.pixel.ASCIIPixel;

class ASCIIGenerator extends Generator[ASCIIPixel] {
  override def next(): ASCIIPixel = {
    val c = gen.nextPrintableChar();
    return new ASCIIPixel(c);
  }
}
