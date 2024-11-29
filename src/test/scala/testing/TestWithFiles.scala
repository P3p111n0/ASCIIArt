package testing

import java.io.File
import java.util.UUID
import scala.io.Source

// stolen from https://gitlab.fit.cvut.cz/BI-OOP/B241/labs/dad-joke-db/-/blob/lab11/src/test/scala/helpers/TestWithFiles.scala

trait TestWithFiles
{
  val testFolder = "./src/test/test_output/"

  def ensureDeleted(filePath: String): Unit =
  {
    val file = new File(filePath)
    if (file.exists())
      if (!file.delete())
        throw new Exception("Could not delete " + filePath)
  }

  def ensureCreated(filePath: String): Unit =
  {
    val file = new File(filePath)

    ensureDeleted(filePath)

    if (!file.createNewFile())
      throw new Exception("Could not create " + filePath)
  }

  def getTestFile: String = testFolder + UUID.randomUUID().toString + ".txt"

  def assertFileContent(filePath: String, content: String): Unit =
  {
    val source = Source.fromFile(filePath)
    val text = source.mkString

    source.close()

    assert(text == content)
  }
}
