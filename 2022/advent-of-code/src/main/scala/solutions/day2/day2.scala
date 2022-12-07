package solutions.day2

import scala.io.Source

object day2 {
  private val ROCK = "A"
  private val PAPER = "B"
  private val SCISSORS = "C"
  private val ROCKx = "X"
  private val PAPERy = "Y"
  private val SCISSORSz = "Z"
  private val WIN: Int = 6
  private val DRAW: Int = 3
  private val LOST: Int = 0
  private val SHAPE_POINTS: Map[String, Int] = Map("X" -> 1, "Y" -> 2, "Z" -> 3)
  private val LOOKUP_NAME: Map[String, String] = Map("A" -> "ROCK", "B" -> "PAPER", "C" -> "SCISSOR", "X" -> "ROCK", "Y" -> "PAPER", "Z" -> "SCISSOR")
  private val LOOKUPS = Map(
    "A" -> Map("losesTo" -> "Y", "beats" -> "Z", "draw" -> "X"),
    "B" -> Map("losesTo" -> "Z", "beats" -> "X", "draw" -> "Y"),
    "C" -> Map("losesTo" -> "X", "beats" -> "Y", "draw" -> "Z")
  )
  private def calculateScore(opponentShape: String, yourShape: String): Int = {
    println(s"calculateScore: ${LOOKUP_NAME(opponentShape)} ${yourShape}")
    val score = (opponentShape, yourShape) match {
      case (ROCK, ROCKx) | (PAPER, PAPERy) | (SCISSORS, SCISSORSz) => DRAW
      case (ROCK, PAPERy) => WIN
      case (ROCK, SCISSORSz) => LOST
      case (PAPER, ROCKx) => LOST
      case (PAPER, SCISSORSz) => WIN
      case (SCISSORS, ROCKx) => WIN
      case (SCISSORS, PAPERy) => LOST
      case (_, _) => throw new MatchError("invalid pair to match")
    }
    score + SHAPE_POINTS(yourShape)
  }

  private def calculateScoreDecoded(opponentShape: String, roundResult: String): Int = {
    println(s"calculateScoreDecoded ${LOOKUP_NAME(opponentShape)} ${roundResult}")
    val score = roundResult match {
      case "Y" => calculateScore(opponentShape, LOOKUPS(opponentShape)("draw"))
      case "X" => calculateScore(opponentShape, LOOKUPS(opponentShape)("beats"))
      case "Z" => calculateScore(opponentShape, LOOKUPS(opponentShape)("losesTo"))
      case _ => throw new MatchError("invalid match")
    }
    score
  }

  def main(argv: Array[String]): Unit = {
    /*
    Col 1:
      A - Rock (1p)
      B - Paper (2p)
      C - Scissors (3p)
    Col 2
      X - Rock
      Y - Paper
      Z - Scissors
     Points:
      Lose - 0p
      Draw - 3p
      Win - 6p
     */

    val bufferedSource = Source.fromFile("src/main/scala/solutions/day2/input.txt")

    var totalSum = 0
    var totalItems = 0
    bufferedSource.getLines().foreach(line => {
      totalItems+=1
      val Array(opponentShape, myShape) = line.split(" ").map(x => x.trim)
      val score = calculateScoreDecoded(opponentShape, myShape)
      totalSum += score
      // println(s"${LOOKUP_NAME(opponentShape)} ${LOOKUP_NAME(myShape)} => $score (Total: $totalSum)")
    } )

    println(s"Total: $totalSum")
    println(s"total items $totalItems")

    bufferedSource.close()

  }
}
