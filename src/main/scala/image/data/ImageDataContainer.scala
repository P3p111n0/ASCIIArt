package image.data;

import image.pixel.Pixel
import error.Error

trait ImageDataContainer[T <: Pixel] {
  def width(): Int;
  def height(): Int;
  def at(i : Int, j : Int): Option[T];
  def set(i : Int, j : Int, value : T): Option[ImageDataContainer[T]];
  def map[U <: Pixel](fn : T => U): ImageDataContainer[U];
  def swap(row1 : Int, col1 : Int, row2 : Int, col2 : Int): Either[ImageDataContainer[T], Error];
}
