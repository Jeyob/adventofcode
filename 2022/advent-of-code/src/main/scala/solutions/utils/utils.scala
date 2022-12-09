package solutions.utils

object utils {
  private val MATRIX_NAVIGATION = Map[String, (Int, Int)](
    "U" -> (-1, 0),
    "D" -> (1, 0),
    "L" -> (0, -1),
    "R" -> (0, 1)
  )
  case class Point(x: Int, y: Int)
  def movePoint(x: Int, y: Int, direction: String): Point = {
    val dirOperand = MATRIX_NAVIGATION(direction)
    Point(x + dirOperand._1, y + dirOperand._2)
  }

}
