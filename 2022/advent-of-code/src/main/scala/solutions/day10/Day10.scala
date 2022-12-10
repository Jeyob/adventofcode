package solutions.day10

import scala.io.Source

object Day10 {
  /**
   * noop - 1 cycles
   * Addx - 2 cycles
   *
   * Signal strength = cycle * X
   * 20th, 60th, 100th, etc
   *
   * */

  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day10/input.txt")
    var x = 1 // register start value
    var cycle = 0
    var crtCycle = 0
    val cycleSet = Set[Int](20, 60, 100, 140, 180, 220)

    val signalStrengthSum = bufferedSource.getLines().foldLeft(0) { (sum, line) =>
      var signalStrength = 0
      val instruction = line.trim.split(" ")

      for (_ <- 0 until  instruction.length) {
        cycle += 1
        if (cycleSet.contains(cycle)) {
          signalStrength = cycle * x
        }
        if (x-1 == crtCycle || x == crtCycle || x+1 == crtCycle) {
          print("#")
        } else {
          print(".")
        }
        crtCycle += 1
        // new row
        if (crtCycle % 40 == 0) {
          print("\n")
          crtCycle = 0
        }
      }
      x += (if (instruction.length > 1) instruction(1).toInt else 0)
      sum + signalStrength
    }
    println()
    println(s"x: $x")
    println(s"signalStrengthSum: $signalStrengthSum")

    bufferedSource.close()
  }

}
