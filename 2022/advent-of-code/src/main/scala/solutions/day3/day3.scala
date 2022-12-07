package solutions.day3

import scala.io.Source

object day3 {
  private val prioLookup = (Range.inclusive(97, 122).++(Range.inclusive(65, 90)).map(x => x.toChar) zip Range.inclusive(1, 52)).toMap[Char, Int]

  private def getFrequency(items: String): Map[Char, Int] = {
    items.foldLeft[Map[Char, Int]](Map()) { (freqMap, item) =>
      if (freqMap.contains(item)) {
        freqMap + (item -> (freqMap(item) + 1))
      } else {
        freqMap + (item -> 1)
      }
    }
  }
  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day3/input.txt")

    // Problem 1
    val totalSum1 = bufferedSource.getLines().foldLeft(0) {(total, line) =>
      val (compartA, compartB) = line.splitAt(math.floorDiv(line.length, 2))
      val frequencyA = getFrequency(compartA)
      val frequencyB = getFrequency(compartB)
      //println(frequencyA.keySet.intersect(frequencyB.keySet))
      total + frequencyA.keySet.intersect(frequencyB.keySet).foldLeft[Int](0) { (s, c) => s + prioLookup(c)}
    }
    println(s"Problem 1: $totalSum1")

    // Problem 2
    val lines = bufferedSource.reset().getLines().toList
    val badges = lines.grouped(3).map { (l) =>
      val badgeType = l(0).toCharArray.toSet
        .intersect(l(1).toCharArray.toSet)
        .intersect(l(2).toCharArray.toSet)
      badgeType.head
    }
    val totalSum2 = badges.map(x => prioLookup(x)).sum
    println(s"Problem 2 $totalSum2")

    bufferedSource.close()
  }
}
