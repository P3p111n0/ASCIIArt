package io.saver

import error.Error
import image.Image
import image.pixel.Pixel

trait Saver[T <: Pixel] {
  def save(img : Image[T]): Option[Error]; 
}
