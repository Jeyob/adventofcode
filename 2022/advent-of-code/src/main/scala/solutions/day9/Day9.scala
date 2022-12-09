package solutions.day9

import scala.io.Source
import scala.collection.mutable

case class Point(x: Int, y: Int)
class Knot(xStart: Int, yStart: Int) {
  private var _x: Int = xStart
  private var _y: Int = yStart

  def move(direction: String): Point = {
    direction match {
      case "U" => _y += 1
      case "D" => _y -= 1
      case "L" => _x -= 1
      case "R" => _x += 1
      case "RD" => _x +=1; _y-=1
      case "RU" => _x +=1; _y+=1
      case "LD" => _x -=1; _y-=1
      case "LU" => _x -=1; _y+=1
      case _ => throw new MatchError(s"Not supported direction $direction")
    }
    Point(_x, _y)
  }

  def distance(p: Knot): Int = {
    math.sqrt(math.pow(this._x - p.x, 2) + math.pow(this._y - p.y, 2)).toInt
  }

  def x: Int = this._x
  def y: Int = this._y
  def position: Point = Point(this._x, this._y)
}

object Day9 {

  def knotMove(head: Knot,  tail: Knot): Unit = {
    val d = head.distance(tail)
    if (d > 1) { // need to move tail
      if (head.x != tail.x && head.y != tail.y) { // move tail diagonally
        (head.x - tail.x > 0, head.y - tail.y > 0) match {
          case (true, true) => tail.move("RU")
          case (true, false) => tail.move("RD")
          case (false, false) => tail.move("LD")
          case (false, true) => tail.move("LU")
        }
      } else { // move in row or col
        if (head.x - tail.x > 1) {
          tail.move("R")
        }
        if (head.x - tail.x < -1) {
          tail.move("L")
        }
        if (head.y - tail.y > 1) {
          tail.move("U")
        }
        if (head.y - tail.y < -1) {
          tail.move("D")
        }
      }
    }
  }
  private def simulateKnotMove(movements: List[(String, Int)], nKnots: Int = 2): Int = {
    val tailMoves = mutable.ListBuffer[Point]()
    val knots = List.fill[Knot](nKnots)(new Knot(0, 0))
    tailMoves.addOne(knots.last.position)

    for (move <- movements) {
      for (_ <- 1 to move._2) {
        knots.head.move(move._1)
        for (pos <- 1 until knots.length) {
          knotMove(knots(pos-1), knots(pos))
        }
        tailMoves.addOne(knots.last.position)
        }
      }
    val moves = tailMoves.groupBy(item => (item.x, item.y))
    moves.size
  }


  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day9/input.txt")

    val lines = bufferedSource.getLines().map(l => {
      val Array(dir, times) = l.split(" ")
      (dir, times.toInt)
    }).toList

    val part1 = simulateKnotMove(lines)
    val part2 = simulateKnotMove(lines, 10)

    println(s"Tail moves (part 1): $part1")
    println(s"Tail moves (part 2): $part2")
    bufferedSource.close()
  }
}
