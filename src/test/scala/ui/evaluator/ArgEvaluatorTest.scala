package ui.evaluator

import org.scalatest.funsuite.AnyFunSuite
import ui.args.*
import error.Error
import image.pixel.ASCIIPixel
import io.loader.*
import io.saver.{TextFileSaver, *}
import transform.grayscale.{GrayscaleInverter, *}
import testing.TestUtils
import transform.ascii.*
import transform.flip.{XFlip, YFlip}
import transform.scaling.*
import transform.*
import transform.rotation.Rotation

class ArgEvaluatorTest extends AnyFunSuite {
  private val base = TestUtils.get_test_images_path();
  test("gets path image loader - png") {
    val path = base + "png/bobinka.png"
    val args = Seq[Arg](ImageArg(path));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(value) => value match {
        case loader: PNGLoader =>
        case _ => fail("Expected PNGLoader");
      }
      case _ => fail("Failed to get png loader.");
    }
  }

  test("gets path image loader - jpg") {
    val path = base + "jpg/bobinka.jpg"
    val args = Seq[Arg](ImageArg(path));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(value) => value match {
        case loader: JPGLoader =>
        case _ => fail("Expected JPGLoader.");
      }
      case _ => fail("Failed to get jpg loader.");
    }
  }

  test("gets path image loader - gif") {
    val path = base + "gif/bobinka.gif"
    val args = Seq[Arg](ImageArg(path));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(value) => value match {
        case loader: GIFLoader =>
        case _ => fail("Expected GIFLoader.");
      }
      case _ => fail("Failed to get gif loader.");
    }
  }

  test("fails to get loader - invalid extension") {
    val path = "./README.md";
    val args = Seq[Arg](ImageArg(path));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(_) => fail("Got loader with invalid extension.");
      case Right(e) => assert(e.msg == "Invalid or unsupoorted image extension.");
    }
  }

  test("fails to get loader - no args") {
    val args = Seq[Arg](InvertArg, BrightnessArg(10), XFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(_) => fail("Got loader without args.");
      case Right(e) => assert(e.msg == "No image loading method specified.");
    }
  }

  test("fails to get loader - multiple args") {
    val path = base + "png/bobinka.png"
    val args = Seq[Arg](XFlipArg, ImageArg(path), RandomImageArg, YFlipArg, ImageArg(path));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_loader();
    result match {
      case Left(_) => fail("Got loader with multiple loaders specified.");
      case Right(e) => assert(e.msg == "Multiple image loading methods specified.");
    }
  }

  test("gets encoder - basic") {
    val args = Seq[Arg](RotateArg(10), ImageArg(""), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(GrayscaleEncoder) =>
      case _ => fail("Failed to get basic grayscale encoder.");
    }
  }

  test("gets encoder - invert") {
    val args = Seq[Arg](ImageArg(""), InvertArg, XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(GrayscaleInverter(GrayscaleEncoder)) =>
      case _ => fail("Failed to get invert grayscale encoder.");
    }
  }

  test("gets encoder - brightness") {
    val rand = TestUtils.get_random_int();
    val args = Seq[Arg](ImageArg(""), BrightnessArg(rand), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(BrightnessModifier(rand, GrayscaleEncoder)) =>
      case _ => fail("Failed to get brightness grayscale encoder.");
    }
  }

  test("gets encoder - multiple encoders") {
    val args = Seq[Arg](InvertArg, ImageArg(""), InvertArg, BrightnessArg(10), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(BrightnessModifier(10, GrayscaleInverter(GrayscaleInverter(GrayscaleEncoder)))) =>
      case _ => fail("Failed to get multiple encoders.");
    }
  }

  test("gets encoder - multiple encoders 2") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), InvertArg, XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(GrayscaleInverter(BrightnessModifier(10, GrayscaleEncoder))) =>
      case _ => fail("Failed to get multiple encoders.");
    }
  }

  test("fails to get encoder - multiple brightness filters") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BrightnessArg(20), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_encoder();
    result match {
      case Left(_) => fail("Got encoder with multiple brightness filters.");
      case Right(e) => assert(e.msg == "Argument brightness cannot be used more than once.");
    }
  }

  test("gets ascii map - basic") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), RotateArg(-15), ScaleArg(0.1));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(StandardASCIIMap) =>
      case _ => fail("Failed to get basic ascii map.");
    }
  }

  test("gets ascii map - linear") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), RotateArg(-15), ScaleArg(0.1));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(StandardASCIIMap) =>
      case _ => fail("Failed to get linear ascii map.");
    }
  }

  test("gets ascii map - nonlinear") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), RotateArg(-15), ScaleArg(0.1), BuiltinTableArg("nonlinear"));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(NonlinearASCIIMap(".aasdfas+*#%@")) =>
      case _ => fail("Failed to get nonlinear ascii map.");
    }
  }

  test("get ascii map - simple") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("simple"), RotateArg(-15), ScaleArg(0.1));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(ASCIIIntMap(" .:-=+*#%@")) =>
      case _ => fail("Failed to get simple ascii map.");
    }
  }

  test("fails to get ascii map - multiple tables") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("._."), CustomTableArg("xd"));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(_) => fail("Got ascii map with multiple tables.");
      case Right(e) => assert(e.msg == "Multiple transformation tables specified.");
    }
  }

  test("fails to get ascii map - unknown table") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("this table doesn't exist"));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_ascii_map();
    result match {
      case Left(_) => fail("Got ascii map with unknown table.");
      case Right(e) => assert(e.msg == "Unknown builtin table this table doesn't exist");
    }
  }

  test("gets filters - no filters") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("simple"));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_filters();
    result match {
      case Left(list) => assert(list.isEmpty);
      case _ => fail("Failed to get empty list of filters.");
    }
  }

  test("gets filters - basic") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("simple"), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_filters();
    val expected = Seq(new XFlip(), new YFlip());
    result match {
      case Left(expected) =>
      case _ => fail("Failed to get basic list of filters.");
    }
  }

  test("get filters - multiple filters") {
    val args = Seq[Arg](ImageArg(""), RotateArg(90), BuiltinTableArg("simple"), XFlipArg, YFlipArg, ScaleArg(4));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_filters();
    val expected = Seq(Rotation(90), XFlip(), YFlip(), QuarterScaler());
    result match {
      case Left(expected) =>
      case _ => fail("Failed to get multiple filters.");
    }
  }

  test("get filters - multiple filters 2") {
    val args = Seq[Arg](RandomImageArg, ScaleArg(0.25), BuiltinTableArg("simple"), YFlipArg, ScaleArg(4), RotateArg(180));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_filters();
    val expected = Seq(QuarterScaler(), YFlip(), QuadrupleScaler(), Rotation(180));
    result match {
      case Left(expected) =>
      case _ => fail("Failed to get multiple filters.");
    }
  }

  test("get exporters - no args") {
    val args = Seq[Arg](XFlipArg, BrightnessArg(10), ImageArg("????"));
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_exporters();
    result match {
      case Left(Seq(ConsoleSaver)) =>
      case _ => fail("Failed to get default console saver.");
    }
  }

  test("get exporters - file") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), BuiltinTableArg("simple"), FileExportArg("path/to/file"), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_exporters();
    result match {
      case Left(Seq(value)) => value match {
        case saver: TextFileSaver => assert(saver.path == "path/to/file");
        case _ => fail("Expected TextFileSaver.");
      }
      case _ => fail("Failed to get file saver.");
    }
  }

  test("get exporters - console") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), ConsoleExportArg, BuiltinTableArg("simple"), XFlipArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_exporters();
    result match {
      case Left(Seq(ConsoleSaver)) =>
      case _ => fail("Failed to get console saver.");
    }
  }

  test("get exporters - multiple exporters") {
    val args = Seq[Arg](ImageArg(""), BrightnessArg(10), FileExportArg("path/to/file"), BuiltinTableArg("simple"), XFlipArg, ConsoleExportArg, YFlipArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_exporters();
    val expected = Seq(TextFileSaver("path/to/file"), ConsoleSaver);
    result match {
      case Left(expected) =>
      case _ => fail("Failed to get multiple exporters.");
    }
  }

  test("get exporters - multiple exporters 2") {
    val args = Seq[Arg](FileExportArg("path2"), ImageArg(""), BrightnessArg(10), ConsoleExportArg,
      BuiltinTableArg("simple"), XFlipArg, FileExportArg("path/to/file"), YFlipArg, ConsoleExportArg);
    val evaluator = new ArgEvaluator(args);
    val result = evaluator.get_exporters();
    val expected = Seq(TextFileSaver("path2"), ConsoleSaver, TextFileSaver("path/to/file"), ConsoleSaver);
    result match {
      case Left(expected) =>
      case _ => fail("Failed to get multiple exporters.");
    }
  }

}
