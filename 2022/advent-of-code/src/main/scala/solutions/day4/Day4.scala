package solutions.day4

import scala.io.Source

case class Section(
                    lbound: Int,
                    ubound: Int
                  )

object Section {
  def apply(sectionString: String): Section = {
    val Array(lower, upper) = (sectionString.split("-"))
    Section(lower.toInt, upper.toInt)
  }
}
object Day4 {
  type PartiallyOverlapping = Boolean
  type FullyOverlapping = Boolean
  private def isOverlapping(range1: String, range2: String): PartiallyOverlapping = {
    val (section1, section2) = (Section(range1), Section(range2))
    //println(s"${section1.ubound} >= ${section2.lbound} && ${section1.ubound} <= ${section2.ubound}")
    (section1.ubound >= section2.lbound && section1.ubound <= section2.ubound) ||
      (section2.ubound >= section1.lbound && section2.ubound <= section1.ubound)
  }

  private def fullyOverlapping(range1: String, range2: String): FullyOverlapping = {
    val (section1, section2) = (Section(range1), Section(range2))
    (section1.lbound >= section2.lbound && section1.ubound <= section2.ubound) ||
      (section2.lbound >= section1.lbound && section2.ubound <= section1.ubound)
  }

  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day4/input.txt")
    var numFullyOverlapping = 0
    var numOverlapping = 0
    for(line <- bufferedSource.getLines()) {
      val Array(elfOne, elfTwo) = line.trim.split(",")
      if (isOverlapping(elfOne, elfTwo)) numOverlapping += 1
      if (fullyOverlapping(elfOne, elfTwo)) numFullyOverlapping += 1
    }
    //problem 1
    println(s"numFullyOverlapping: $numFullyOverlapping")
    //problem 2
    println(s"numOverlapping: $numOverlapping")
    bufferedSource.close()
  }
}
