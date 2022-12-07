package solutions.day6

import scala.collection.mutable
import scala.io.Source
import scala.util.control.Breaks.{break, breakable}
object Day6 {

  def getNumCharsToMarker(line: String) = {
    val set = mutable.Set.empty[Char]
    var charsRead: Int = 0
    breakable {
      for (i <- 0 until line.length - 3) {
        set.addAll(List(line.charAt(i), line.charAt(i+1), line.charAt(i+2), line.charAt(i+3)))

        if (set.size == 4) {
          charsRead = i+3+1
          break
        } else {
          set.clear()
        }
      }
    }
    charsRead
  }

  def getNumCharsToMessageMarker(line: String) = {
    val set = mutable.Set.empty[Char]
    var charsRead: Int = 0
    breakable {
      for (i <- 0 until line.length - 13) {
        val lookAhead = Range(i, i + 14).map(x => line.charAt(x))
        set.addAll(lookAhead)

        if (set.size == 14) {
          charsRead = i + 14
          break
        } else {
          set.clear()
        }
      }
    }
    charsRead
  }
  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("src/main/scala/solutions/day6/input.txt")
    val line = bufferedSource.bufferedReader().readLine()

    val packetStart = getNumCharsToMarker(line)
    val messageStart = getNumCharsToMessageMarker(line)

    println(packetStart)
    println(messageStart)
    bufferedSource.close()
  }

}
