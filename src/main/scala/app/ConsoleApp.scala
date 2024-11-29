package app

import ui.parser.StreamParser
import ui.evaluator.ArgEvaluator
import image.Image
import transform.ascii.ASCIITransform
import transform.ascii.ToASCIITransform
import java.io.ByteArrayInputStream
import scala.util.boundary, boundary.break;
import error.Error;


class ConsoleApp(console_args: Array[String]) extends App {
  def run(): Option[error.Error] = {
    val combined_args = console_args.mkString(" ");
    val stream = new ByteArrayInputStream(combined_args.getBytes());
    val parser = new StreamParser(stream);
    val args = parser.parse() match {
      case Left(a) => a;
      case Right(e) => {
        return Some(e);
      }
    }
    val evaluator = new ArgEvaluator(args);
    val loader = evaluator.get_loader() match {
      case Left(loader) => loader;
      case Right(e) => {
        return Some(e);
      }
    }
    var img = loader.load() match {
      case Left(img) => img;
      case Right(e) => {
        return Some(e);
      }
    };
    val encoder = evaluator.get_encoder() match {
      case Left(enc) => enc;
      case Right(e) => {
        return Some(e);
      }
    }
    val ascii_map = evaluator.get_ascii_map() match {
      case Left(map) => map;
      case Right(e) => {
        return Some(e);
      }
    }
    val transform = new ASCIITransform(new ToASCIITransform(encoder, ascii_map));
    var ascii_img = transform(img);
    val filters = evaluator.get_filters() match {
      case Left(f) => f;
      case Right(e) => {
        return Some(e);
      }
    }

    for (f <- filters) {
      ascii_img = f(ascii_img);
    }

    val exporters = evaluator.get_exporters() match {
      case Left(exp) => exp;
      case Right(e) => {
        return Some(e);
      }
    }

    val e: Option[Error] = boundary {
      for (exporter <- exporters) {
        exporter.save(ascii_img) match {
          case Some(e) => {
            break(Some(e));
          }
          case None =>
        }
      }
      None;
    }
    return e;
  }
}
