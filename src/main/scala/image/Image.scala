package image;

import image.ImageData;
import image.pixel.Pixel

class Image[+T <: Pixel] private[image](val data : Vector[Vector[T]]) {}
