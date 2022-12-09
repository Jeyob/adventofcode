package solutions.day8

import solutions.utils.utils.{Point, movePoint}

import scala.collection.mutable
import scala.io.Source
import scala.util.control.Breaks.{break, breakable}

object Day8 {

  type Grid = List[List[Int]]
  private def buildMatrix(itr: Iterator[String]): Grid = {
    val converted = itr.map { line =>
        line.split("").map(x => x.toInt).toList
    }.toList

    val cols = converted.head.length + 2

    // adds padding of -1s around the matrix
    List.fill(cols)(-1) +: converted.map { row =>
      -1 +: row :+ -1
    } :+ List.fill(cols)(-1)
  }

  private def isEdgePoint(x: Int, y: Int, grid: Grid): Boolean = {
    var neighborEdge = false
    List("U", "D", "L", "R").foreach( direction => {
      val p = movePoint(x, y, direction)
      if (grid(p.x)(p.y) == -1) {
        neighborEdge = true
      }
    })
    neighborEdge
  }

  private def maxScenicScore(grid: Grid): Int = {
    var maxScore = 0
    for (row <- 1 until grid.length - 1) {
      for (col <- 1 until grid.head.length - 1) {
        val score = if (isEdgePoint(row, col, grid)) 0 else List("U", "D", "L", "R").foldLeft(1) { (scenicScore, direction) =>
          var currPoint = Point(row, col)
          var cntTrees = 0
          breakable {
            while (grid(currPoint.x)(currPoint.y) != -1) {
              currPoint = movePoint(currPoint.x, currPoint.y, direction)
              cntTrees = if(grid(currPoint.x)(currPoint.y) != -1) cntTrees + 1 else cntTrees
              if (grid(currPoint.x)(currPoint.y) >= grid(row)(col)) {
                // stop since we know it can't be visible in that direction
                break
              }
            }
          }
          scenicScore * cntTrees
        }
        if (maxScore < score) {
          maxScore = score
        }
      }
    }
    maxScore
  }
  private def visibleTrees(grid: Grid): Int = {
    val visibleTrees = mutable.Map[(Int, Int), Int]()

    for (row <- 1 until grid.length-1) {
      for (col <- 1 until grid.head.length-1) {
        if (isEdgePoint(row, col, grid)) {
          visibleTrees.addOne((row, col), grid(row)(col))
        } else {
          List("U", "D", "L", "R").foreach(direction => {
            var currPoint = Point(row, col)
            breakable {
              while (grid(currPoint.x)(currPoint.y) != -1) {
                currPoint = movePoint(currPoint.x, currPoint.y, direction)
                if (grid(currPoint.x)(currPoint.y) >= grid(row)(col)) {
                  // stop since we know it can't be visible in that direction
                  break
                }
              }
            }
            // if we got through to the edge its visible
            if (grid(currPoint.x)(currPoint.y) == -1) {
              visibleTrees.addOne((row, col), grid(row)(col))
            }
          })
        }
      }
    }
    visibleTrees.size
  }
  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day8/input.txt")
    val mtx = buildMatrix(bufferedSource.getLines())
    // part 1
    val cntVisibleTrees = visibleTrees(mtx)
    println(s"visible trees: $cntVisibleTrees")

    // part 2
    val maxScore = maxScenicScore(mtx)
    println(s"max scenic score: $maxScore")
    bufferedSource.close()
  }

}
