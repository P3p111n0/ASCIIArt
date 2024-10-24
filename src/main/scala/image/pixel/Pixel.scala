package image.pixel

trait Pixel {
  def default(): Pixel;
  def apply(): Pixel;
}
