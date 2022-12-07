package solutions.day1

import scala.collection.mutable.ListBuffer
import scala.io.Source


object Day1 extends App {

  private def countElvCalories(inventory: List[String]): List[Int] = {
    val calories: ListBuffer[Int] = ListBuffer[Int]()
    var currSum: Int = 0
    for (line <- inventory) {
      line match {
        case "" =>
          calories += currSum
          currSum = 0
        case _ =>
          currSum += line.toInt
      }
    }
    if (currSum > 0) {
      calories += currSum
    }
    calories.toList
  }

  private val bufferedSource = Source.fromFile("src/main/scala/solutions/day1/input.txt")
  private val calories = countElvCalories(bufferedSource.getLines().toList).sortWith(_ > _)

  val topElf = calories.head
  val topThree = calories.take(3).sum
  println(s"Top elf sum: $topElf")
  print(s"Top three elves sum: $topThree")

  bufferedSource.close()
}
