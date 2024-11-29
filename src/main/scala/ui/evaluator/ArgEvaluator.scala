package ui.evaluator;

import ui.args.*
import loader.Loader
import image.pixel.RGBPixel
import error.Error
import image.pixel.encoding.Encoder
import transform.ascii.ASCIIMap
import image.pixel.ASCIIPixel
import transform.Transformation
import ui.args.ImageArg
import ui.args.RandomImageArg
import io.loader.{ImageLoader, RandomLoader, JPGLoader, PNGLoader, GIFLoader}
import image.pixel.encoding.RGBDecoder
import image.pixel.generator.RGBGenerator
import transform.grayscale.GrayscaleEncoder
import transform.grayscale.GrayscaleInverter
import transform.grayscale.BrightnessModifier
import transform.ascii.StandardASCIIMap
import transform.ascii.ASCIIIntMap
import error.InternalException
import transform.flip.{XFlip, YFlip}
import transform.scaling.ScalerSelector
import transform.Rotation
import io.saver.{Saver, TextFileSaver, ConsoleSaver}
import transform.ascii.NonlinearASCIIMap
import scala.util.boundary, boundary.break;
import utils.FileUtils;

class ArgEvaluator(private val args : Seq[Arg]) extends Evaluator(args) {
  private val seed = 333;
  override def get_loader(): Either[Loader[RGBPixel], Error] = {
    val matcher = (x : Arg) => x match {
      case x : LoadArg => true;
      case _ => false;
    }
    val filtered = args.filter(matcher);
    filtered.length match {
      case 1 => {
        filtered(0) match {
          case ImageArg(path) => {
            FileUtils.get_file_ext(path) match {
              case Some("jpg") => return Left(new JPGLoader(path, RGBDecoder));
              case Some("png") => return Left(new PNGLoader(path, RGBDecoder));
              case Some("gif") => return Left(new GIFLoader(path, RGBDecoder));
              case _ => return Right(new Error("Invalid or unsupoorted image extension."));
            }
          }
          case RandomImageArg => {
            val generator = RGBGenerator(seed);
            return Left(new RandomLoader(seed, generator));
          } 
        }
      } 
      case 0 => return Right(new Error("No image loading method specified."));
      case _ => return Right(new Error("Multiple image loading methods specified."));
    }

  }

  override def get_encoder(): Either[Encoder[RGBPixel, Int], Error] = {
    val matcher = (x : Arg) => x match {
      case _ : GrayscaleArg => true;
      case _ => false;
    }
    var seen = Set[String]();
    val filtered = args.filter(matcher);
    var encoder : Encoder[RGBPixel, Int] = GrayscaleEncoder;
    val e : Option[Error] = boundary {
      for (arg <- filtered) {
        if (!arg.iterable && seen(arg.name)) {
          break(Some(new Error(s"Argument ${arg.name} cannot be used more than once.")));
        }
        seen = seen + arg.name;
        encoder = arg match {
          case InvertArg => new GrayscaleInverter[RGBPixel](encoder); 
          case BrightnessArg(offset) => new BrightnessModifier[RGBPixel](offset, encoder);
        }
      }
      None
    }

    e match {
      case Some(e) => return Right(e);
      case None =>
    }

    return Left(encoder);
  }

  override def get_ascii_map(): Either[ASCIIMap[Int], Error] = {
    val matcher = (x : Arg) => x match {
      case _ : TableArg => true;
      case _ => false;
    }

    val filtered = args.filter(matcher);
    filtered.length match {
      case 0 => return Left(StandardASCIIMap);
      case 1 => filtered(0) match {
        case BuiltinTableArg(name) => name match {
          case "linear" => return Left(StandardASCIIMap);
          case "nonlinear" => return Left(new NonlinearASCIIMap(" .:-=+*#%@"));
          case "simple" => return Left(new ASCIIIntMap(" .:-=+*#%@"));
          case _ => return Right(new Error(s"Unknown builtin table $name"));
        }
        case CustomTableArg(map) => return Left(new ASCIIIntMap(map)); 
      }
      case _ => return Right(new Error("Multiple transformation tables specified."));
    }
  }

  override def get_filters(): Either[Seq[Transformation[ASCIIPixel, ASCIIPixel]], Error] = {
    val matcher = (x : Arg) => x match {
      case _ : TableArg => false;
      case _ : IOArg => false;
      case _ : GrayscaleArg => false;
      case _ => true;
    }
    val filtered = args.filter(matcher);
    var result = Seq[Transformation[ASCIIPixel, ASCIIPixel]]();
    var seen = Set[String]();
    
    val e : Option[Error] = boundary {
      for (t <- filtered) {
        if (!t.iterable && seen(t.name)) {
          break(Some(new Error(s"Filter $t.name cannot be used more than once.")));
        } 
        seen = seen + t.name;
        val matched_arg : Either[Transformation[ASCIIPixel, ASCIIPixel], Error] = t match {
          case XFlipArg => Left(new XFlip[ASCIIPixel]());
          case YFlipArg => Left(new YFlip[ASCIIPixel]());
          case ScaleArg(factor) => ScalerSelector[ASCIIPixel](factor) match {
            case Left(scaler) => Left(scaler);
            case Right(e) => Right(e);
          }
          case RotateArg(angle) => Rotation[ASCIIPixel](angle) match {
            case Left(rotation) => Left(rotation);
            case Right(e) => Right(e);
          }
          case _ => throw new InternalException("Unhandled arg type.");
        } 
        matched_arg match {
          case Left(transform) => result = result.appended(transform);
          case Right(e) => break(Some(e));
        }
      }
      None;
    }

    e match {
      case Some(e) => return Right(e);
      case None =>
    }

    return Left(result);
  } 

  override def get_exporters(): Either[Seq[Saver[ASCIIPixel]], Error] = {
    val matcher = (x : Arg) => x match {
      case x : ExportArg => true;
      case _ => false;
    }
    val filtered = args.filter(matcher);

    var result = Seq[Saver[ASCIIPixel]]();
    for (saver <- filtered) {
        val new_elem = saver match {
          case FileExportArg(path) => {
            new TextFileSaver(path); 
          }
          case ConsoleExportArg => {
            ConsoleSaver;
          } 
        }
        result = result.appended(new_elem);
    }

    if (result.isEmpty) {
      result = result.appended(ConsoleSaver);
    }

    return Left(result);
  }
}
