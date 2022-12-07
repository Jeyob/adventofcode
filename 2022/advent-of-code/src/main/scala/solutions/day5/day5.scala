package solutions.day5

import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.io.Source
import scala.util.control.Breaks._

object day5 {

  def testPuzzle(): Array[mutable.Stack[String]] = {
    val stacks = Array.fill(3) { mutable.Stack[String]() }
    stacks(0).pushAll(List[String]("[Z]", "[N]"))
    stacks(1).pushAll(List[String]("[M]", "[C]", "[D]"))
    stacks(2).pushAll(List[String]("[P]"))
    stacks
  }

  def realPuzzle(): Array[mutable.Stack[String]] = {
    val stacks = Array.fill(9) {
      mutable.Stack[String]()
    }
    stacks(0).pushAll(List[String]("[G]", "[F]", "[V]", "[H]", "[P]", "[S]"))
    stacks(1).pushAll(List[String]("[G]", "[J]", "[F]", "[B]", "[V]", "[D]", "[Z]", "[M]"))
    stacks(2).pushAll(List[String]("[G]", "[M]", "[L]", "[J]", "[N]"))
    stacks(3).pushAll(List[String]("[N]", "[G]", "[Z]", "[V]", "[D]", "[W]", "[P]"))
    stacks(4).pushAll(List[String]("[V]", "[R]", "[C]", "[B]"))
    stacks(5).pushAll(List[String]("[V]", "[R]", "[S]", "[M]", "[P]", "[W]", "[L]", "[Z]"))
    stacks(6).pushAll(List[String]("[T]", "[H]", "[P]"))
    stacks(7).pushAll(List[String]("[Q]", "[R]", "[S]", "[N]", "[C]", "[H]", "[Z]", "[V]"))
    stacks(8).pushAll(List[String]("[F]", "[L]", "[G]", "[P]", "[V]", "[Q]", "[J]"))
    stacks
  }

  def rearrange(stacks: Array[mutable.Stack[String]], nItems: Int, fromStack: Int, toStack: Int): Array[mutable.Stack[String]] = {
    val copy = stacks.clone()
    for (_ <- 1 to nItems) {
      copy(toStack-1).push(copy(fromStack-1).pop())
    }
    copy
  }

  def rearrange2(stacks: Array[mutable.Stack[String]], nItems: Int, fromStack: Int, toStack: Int): Array[mutable.Stack[String]] = {
    val copy = stacks.clone()
    val lifo = mutable.Stack[String]()
    for (_ <- 1 to nItems) {
      lifo.push(copy(fromStack - 1).pop())
    }
    for (_ <- 1 to nItems) {
      copy(toStack - 1).push(lifo.pop())
    }
    copy
  }

  def getTopItems(stacks: Array[mutable.Stack[String]]): String = {
    val topItems = new StringBuilder()
    stacks.foreach {
      s => topItems ++= s.pop()
    }
    topItems.toString()
  }

  def main(args: Array[String]): Unit = {
    var stacks: Array[mutable.Stack[String]] = realPuzzle()
    val rearrangeCmd = """move (\d+) from (\d+) to (\d+)""".r
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day5/input.txt")
    val lineStack = mutable.Stack[String]()

      for (line <- bufferedSource.getLines()) {
        val matches = (rearrangeCmd.findAllIn(line).matchData map {
          m => m.subgroups
        }).toList
        stacks = rearrange2(stacks, matches(0)(0).toInt, matches(0)(1).toInt, matches(0)(2).toInt)
      }
      println(getTopItems(stacks))

    bufferedSource.close()
  }
}
