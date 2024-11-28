package transform.ascii;

import image.pixel.{RGBPixel, ASCIIPixel};
import testing.MockGrayscale;
import org.scalatest.funsuite.AnyFunSuite;
import image.Image;
import image.data.ImageData
import testing.{MockRgbAsciiTransform, MockToASCIIPixel};

class ASCIITransformTest extends AnyFunSuite {
  test("Transforms img to ascii") {
    val zero_pixel = RGBPixel();
    val white_pixel = RGBPixel(255, 255, 255) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }
    val idk_pixel = RGBPixel(127, 85, 13) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }
    val vec = Vector(Vector(zero_pixel, white_pixel, idk_pixel));

    val space = ASCIIPixel(' ') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }
    val dollar = ASCIIPixel('$') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }
    val slash = ASCIIPixel('/') match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }

    val target_vec = Vector(Vector(space, dollar, slash));
    val img_data = ImageData(vec) match {
        case Left(value) => value;
        case Right(value) => fail(value.msg);
    }
    val img = Image(img_data);
    val transform = new ASCIITransform(new MockRgbAsciiTransform(MockGrayscale, MockToASCIIPixel));
    val ascii_img = transform(img);

    assert(ascii_img.width() == 1);
    assert(ascii_img.height() == 3);
    for (pos <- ascii_img.iterate()) {
        val target = target_vec(pos.row)(pos.col);
        assert(pos.value == target);
    }
  }
}