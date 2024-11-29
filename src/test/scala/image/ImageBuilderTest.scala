package image;

import org.scalatest.funsuite.AnyFunSuite
import testing.TestUtils;
import testing.MockPixel

class ImageBuilderTest extends AnyFunSuite {
  test("Constructs") {
    for (_ <- 0 until TestUtils.nshots) {
      val dims = TestUtils.get_random_dims();
      val builder =
        ImageBuilder(dims(0), dims(1), TestUtils.get_random_pixel()) match {
          case Left(value) =>
          case _           => fail("Failed to create ImageBuilder")
        }
    }
  }

  test("Fails") {
    ImageBuilder(-1, 0, TestUtils.get_random_pixel()) match {
      case Right(_) =>
      case _        => fail("Failed to catch negative width")
    }

    ImageBuilder(0, -1, TestUtils.get_random_pixel()) match {
      case Right(_) =>
      case _        => fail("Failed to catch negative height")
    }
  }

  test("Gets") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val fill = TestUtils.get_random_pixel();
    val builder = ImageBuilder(width, height, fill) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    };

    for (_ <- 0 until TestUtils.nshots) {
      val inbounds_dims = TestUtils.get_inbounds_dims(width, height);
      val pixel = builder.get(inbounds_dims(0), inbounds_dims(1)) match {
        case Left(value) => value;
        case _           => fail("Pixel out of bounds");
      }

      assert(pixel == fill);
    }

    builder.get(-1, 0) match {
      case Left(_) => fail("Get success with negative dims");
      case _       =>
    }

    builder.get(0, -1) match {
      case Left(_) => fail("Get success with negative dims");
      case _       =>
    }

    builder.get(width, height) match {
      case Left(value) => fail("Get success with dims out of bounds");
      case _           =>
    }
  }

  test("Sets") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val fill = TestUtils.get_random_pixel();
    val builder = ImageBuilder(width, height, fill) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    };

    for (_ <- 0 until TestUtils.nshots) {
      val inbounds_dims = TestUtils.get_inbounds_dims(width, height);
      val new_pixel = TestUtils.get_random_pixel();
      builder.set(inbounds_dims(0), inbounds_dims(1), new_pixel) match {
        case Left(value) => {
          val pixel = value.get(inbounds_dims(0), inbounds_dims(1)) match {
            case Left(value) => value;
            case _ => fail("Pixel out of bounds");
          }
          assert(pixel == new_pixel);
        }
        case Right(value) => fail(value.msg);
      }
    }

    builder.set(-1, 0, TestUtils.get_random_pixel()) match {
      case Left(_) => fail("Set success with negative dims");
      case _       =>
    }

    builder.set(0, -1, TestUtils.get_random_pixel()) match {
      case Left(_) => fail("Set success with negative dims");
      case _       =>
    }

    builder.set(width, height, TestUtils.get_random_pixel()) match {
      case Left(value) => fail("Set success with dims out of bounds");
      case _           =>
    }
  }

  test("Swaps") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val fill = TestUtils.get_random_pixel();
    val builder = ImageBuilder(width, height, fill) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    };

    for (_ <- 0 until TestUtils.nshots) {
      val first_dims = TestUtils.get_inbounds_dims(width, height);
      val second_dims = TestUtils.get_inbounds_dims(width, height);
      val first = builder.get(first_dims(0), first_dims(1)) match {
        case Left(value) => value;
        case _           => fail("First pixel out of bounds");
      }

      val second = builder.get(second_dims(0), second_dims(1)) match {
        case Left(value) => value;
        case _           => fail("Second pixel out of bounds");
      }

      builder.swap(first_dims(0), first_dims(1), second_dims(0), second_dims(1)) match {
        case Right(value) => fail(value.msg);
        case Left(value) =>
          val new_first = value.get(second_dims(0), second_dims(1)) match {
            case Left(value) => value;
            case _           => fail("First pixel out of bounds");
          }

          val new_second = value.get(first_dims(0), first_dims(1)) match {
            case Left(value) => value;
            case _           => fail("Second pixel out of bounds");
          }

          assert(new_first == second);
          assert(new_second == first);
      }
    }

    builder.swap(-1, 0, 0, 0) match {
      case Left(_) => fail("Swap success with negative dims");
      case _       =>
    }

    builder.swap(0, 0, width, height) match {
      case Left(value) => fail("Swap success with dims out of bounds");
      case _           =>
    }
  }

  test("Maps") {
    val dims = TestUtils.get_random_dims();
    val width = dims(0);
    val height = dims(1);
    val fill = TestUtils.get_random_pixel();
    val builder = ImageBuilder(width, height, fill) match {
      case Left(value)  => value;
      case Right(value) => fail(value.msg);
    };

    val transform = (p : MockPixel) => new MockPixel(p.value * 2 / 3)
    val new_builder = builder.map(transform);

    for (i <- 0 until width) {
      for (j <- 0 until height) {
        val pixel = new_builder.get(i, j) match {
          case Left(value) => value;
          case _           => fail("Pixel out of bounds");
        }
        val old_pixel = builder.get(i, j) match {
          case Left(value) => value;
          case _           => fail("Pixel out of bounds");
        }
        assert(pixel == transform(old_pixel));
      }
    }
  }
}
