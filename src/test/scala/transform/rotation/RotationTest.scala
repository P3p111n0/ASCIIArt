package transform.rotation;

import image.data.ImageData
import image.{Image, ImageBuilder}
import org.scalatest.funsuite.AnyFunSuite
import testing.{MockPixel, TestUtils}
import transform.rotation.Rotation;

class RotationTest extends AnyFunSuite {
  test("Constructs") {
    Rotation(0) match {
      case Left(_) => ;
      case Right(e) => fail(e.msg);
    }

    Rotation(90) match {
      case Left(_) => ;
      case Right(e) => fail(e.msg);
    }

    Rotation(180) match {
      case Left(_) => ;
      case Right(e) => fail(e.msg);
    }

    Rotation(-90) match {
      case Left(_) => ;
      case Right(e) => fail(e.msg);
    }

    Rotation(-180) match {
      case Left(_) => ;
      case Right(e) => fail(e.msg);
    }

    Rotation(720) match {
      case Left(_) =>
      case Right(e) => fail(e.msg);
    }
  }

  test("Fails") {
    Rotation(45) match {
      case Left(_) => fail("45 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(30) match {
      case Left(_) => fail("30 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(60) match {
      case Left(_) => fail("60 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(135) match {
      case Left(_) => fail("135 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(-45) match {
      case Left(_) => fail("-45 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(-30) match {
      case Left(_) => fail("-30 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(-60) match {
      case Left(_) => fail("-60 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(-135) match {
      case Left(_) => fail("-135 is not divisible by 90.");
      case Right(_) => ;
    }

    Rotation(91) match {
      case Left(_) => fail("91 is not divisible by 90.");
      case Right(_) => ;
    }
  }

  test("Rotates") {
    val (width, height) = TestUtils.get_random_dims();
    val vec = TestUtils.get_random_vector(width, height);
    val data = ImageData(vec) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val img = Image(data);
    val transform = Rotation[MockPixel](90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated = transform(img);
    assert(rotated.width() == img.height());
    assert(rotated.height() == img.width());

    for (pos <- rotated.iterate()) {
      val old_row = img.width() - pos.col - 1;
      val old_col = pos.row;
      val target = data.at(old_row, old_col) match {
        case Some(p) => p;
        case None => fail("Failed to get pixel at (%d, %d).".format(old_row, old_col));
      }
      assert(pos.value == target);
    }

    val rotate_back = Rotation[MockPixel](-90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated_back = rotate_back(rotated);
    assert(rotated_back.width() == img.width());
    assert(rotated_back.height() == img.height());

    for (pos <- rotated_back.iterate()) {
      val target = data.at(pos.row, pos.col) match {
        case Some(p) => p;
        case None => fail("Failed to get pixel at (%d, %d).".format(pos.row, pos.col));
      }
      assert(pos.value == target);
    }

    var previous = TestUtils.get_random_image();
    var current = transform(previous);
    for (i <- 0 until TestUtils.nshots) {
      assert(current.width() == previous.height());
      assert(current.height() == previous.width());
      val builder = ImageBuilder(previous);
      for (pos <- current.iterate()) {
        val old_row = previous.width() - pos.col - 1;
        val old_col = pos.row;
        val target = builder.get(old_row, old_col) match {
          case Left(b) => b;
          case Right(e) => fail(e.msg);
        }
        assert(pos.value == target);
      }
      previous = current;
      current = transform(previous);
    }
  }

  test("Rotates 1x2") {
    val data = ImageData(Vector(Vector(MockPixel(1), MockPixel(2)))) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val img = Image(data);
    val transform = Rotation[MockPixel](90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated = transform(img);
    assert(rotated.width() == 2);
    assert(rotated.height() == 1);

    val rotated_data = rotated.data;
    rotated_data.at(0, 0) match {
      case Some(MockPixel(1)) =>
      case _ => fail("Mismatch at (0, 0).");
    }

    rotated_data.at(1, 0) match {
      case Some(MockPixel(2)) =>
      case _ => fail("Mismatch at (1, 0).");
    }
  }

  test("Rotates 2x3") {
    val data = ImageData(Vector(Vector(MockPixel(1), MockPixel(2), MockPixel(3)),
      Vector(MockPixel(4), MockPixel(5), MockPixel(6)))) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val img = Image(data);
    val transform = Rotation[MockPixel](90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated = transform(img);
    assert(rotated.width() == 3);
    assert(rotated.height() == 2);

    val rotated_data = rotated.data;
    rotated_data.at(0, 0) match {
      case Some(MockPixel(4)) =>
      case _ => fail("Mismatch at (0, 0).");
    }

    rotated_data.at(0, 1) match {
      case Some(MockPixel(1)) =>
      case _ => fail("Mismatch at (0, 1).");
    }

    rotated_data.at(1, 0) match {
      case Some(MockPixel(5)) =>
      case _ => fail("Mismatch at (1, 0).");
    }

    rotated_data.at(1, 1) match {
      case Some(MockPixel(2)) =>
      case _ => fail("Mismatch at (1, 1).");
    }

    rotated_data.at(2, 0) match {
      case Some(MockPixel(6)) =>
      case _ => fail("Mismatch at (2, 0).");
    }

    rotated_data.at(2, 1) match {
      case Some(MockPixel(3)) =>
      case _ => fail("Mismatch at (2, 1).");
    }
  }

  test("Rotates 3x3") {
    val data = ImageData(Vector(
      Vector(MockPixel(1), MockPixel(2), MockPixel(3)),
      Vector(MockPixel(4), MockPixel(5), MockPixel(6)),
      Vector(MockPixel(7), MockPixel(8), MockPixel(9)))
    ) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val img = Image(data);
    val transform = Rotation[MockPixel](90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated = transform(img);
    assert(rotated.width() == 3);
    assert(rotated.height() == 3);

    val rotated_data = rotated.data;
    rotated_data.at(0, 0) match {
      case Some(MockPixel(7)) =>
      case _ => fail("Mismatch at (0, 0).");
    }

    rotated_data.at(0, 1) match {
      case Some(MockPixel(4)) =>
      case _ => fail("Mismatch at (0, 1).");
    }

    rotated_data.at(0, 2) match {
      case Some(MockPixel(1)) =>
      case _ => fail("Mismatch at (0, 2).");
    }

    rotated_data.at(1, 0) match {
      case Some(MockPixel(8)) =>
      case _ => fail("Mismatch at (1, 0).");
    }

    rotated_data.at(1, 1) match {
      case Some(MockPixel(5)) =>
      case _ => fail("Mismatch at (1, 1).");
    }

    rotated_data.at(1, 2) match {
      case Some(MockPixel(2)) =>
      case _ => fail("Mismatch at (1, 2).");
    }

    rotated_data.at(2, 0) match {
      case Some(MockPixel(9)) =>
      case _ => fail("Mismatch at (2, 0).");
    }

    rotated_data.at(2, 1) match {
      case Some(MockPixel(6)) =>
      case _ => fail("Mismatch at (2, 1).");
    }

    rotated_data.at(2, 2) match {
      case Some(MockPixel(3)) =>
      case _ => fail("Mismatch at (2, 2).");
    }
  }

  test("Rotates 1x1") {
    val data = ImageData(Vector(Vector(MockPixel(1)))) match {
      case Left(value) => value;
      case Right(value) => fail(value.msg);
    }
    val img = Image(data);
    val transform = Rotation[MockPixel](90) match {
      case Left(t) => t;
      case Right(e) => fail(e.msg);
    }

    val rotated = transform(img);
    assert(rotated.width() == 1);
    assert(rotated.height() == 1);

    val rotated_data = rotated.data;
    rotated_data.at(0, 0) match {
      case Some(MockPixel(1)) =>
      case _ => fail("Mismatch at (0, 0).");
    }
  }
}
