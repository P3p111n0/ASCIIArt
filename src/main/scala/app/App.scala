package app

import error.Error;

/**
 * An interface for the application.
 */
trait App {
  /**
   * Run the application.
   *
   * @return An error if an error occurred during execution, else None
   */
  def run(): Option[Error];
}
