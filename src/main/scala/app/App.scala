package app

import error.Error;

trait App {
  def run(): Option[Error];
}
