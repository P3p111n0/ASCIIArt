package transform.scaling

import image.Image;
import image.data.ImageData;
import utils.{TestUtils, MockPixel};
import org.scalatest.funsuite.AnyFunSuite;

class ScalerSelectorTest extends AnyFunSuite {
  test("Selects") {
    var factor : Double = 1;
    ScalerSelector(factor) match {
      case Left(_) =>;
      case Right(e) => fail(e.msg);
    }

    factor = 4;
    ScalerSelector(factor) match {
      case Left(_) =>;
      case Right(e) => fail(e.msg);
    }

    factor = 0.25;
    ScalerSelector(factor) match {
      case Left(_) =>;
      case Right(e) => fail(e.msg);
    }

    factor = 0.5;
    ScalerSelector(factor) match {
      case Left(_) => fail("0.5 is not a valid factor.");
      case Right(_) =>;
    }

    factor = 2;
    ScalerSelector(factor) match {
      case Left(_) => fail("2 is not a valid factor.");
      case Right(_) =>;
    }
  }
}
