package transform;

import org.scalatest.funsuite.AnyFunSuite
import image.{Image, ImageBuilder}
import image.data.ImageData
import testing.{MockPixel, TestUtils};

class RotationTest extends AnyFunSuite {
  test("Constructs") {
    Rotation(0) match {
        case Left(_) =>;
        case Right(e) => fail(e.msg);
    }

    Rotation(90) match {
        case Left(_) =>;
        case Right(e) => fail(e.msg);
    }

    Rotation(180) match {
        case Left(_) =>;
        case Right(e) => fail(e.msg);
    }

    Rotation(-90) match {
        case Left(_) =>;
        case Right(e) => fail(e.msg);
    }

    Rotation(-180) match {
        case Left(_) =>;
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
        case Right(_) =>;
    }

    Rotation(30) match {
        case Left(_) => fail("30 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(60) match {
        case Left(_) => fail("60 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(135) match {
        case Left(_) => fail("135 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(-45) match {
        case Left(_) => fail("-45 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(-30) match {
        case Left(_) => fail("-30 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(-60) match {
        case Left(_) => fail("-60 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(-135) match {
        case Left(_) => fail("-135 is not divisible by 90.");
        case Right(_) =>;
    }

    Rotation(91) match {
        case Left(_) => fail("91 is not divisible by 90.");
        case Right(_) =>;
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
        val target = data.at(pos.col, pos.row) match {
          case Some(p) => p;
            case None => fail("Failed to get pixel at (%d, %d).".format(pos.row, pos.col));
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
        val target = builder.get(pos.col, pos.row) match {
          case Left(b) => b;
          case Right(e) => fail(e.msg);
        }
        assert(pos.value == target);
      }
      previous = current;
      current = transform(previous);
    }
  }
}
