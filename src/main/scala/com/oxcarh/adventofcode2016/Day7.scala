
// Day 7: Internet Protocol Version 7
// https://adventofcode.com/2016/day/7

package com.oxcarh.adventofcode2016

object Day7 extends App {

  val input = Tools.loadDayInputAsText(day = 7).split("\n")

  // Solution 1 -----------------------------------------------------------

  println(s"Solution 1: ${input.count(supportsTLS)}")

  // Solution 2 -----------------------------------------------------------

  println(s"Solution 2: ${input.count(supportsSSL)}")

  // ----------------------------------------------------------------------

  def supportsTLS(ip: String): Boolean = {
    hasABBA(ip) && !hasABBAInsideBrackets(ip)
  }

  def hasABBA(ip: String): Boolean = {
    val abbaPattern = """.*([a-z])((?:(?!\1).))(\2)\1.*""".r
    abbaPattern.findAllMatchIn(ip).nonEmpty
  }

  def hasABBAInsideBrackets(ip: String): Boolean = {
    val p1 = """\[([a-z]*)\]""".r
    val p2 = """(?<=\[)[a-z]*([a-z])((?:(?!\1).))(\2)\1[a-z]*(?=\])""".r
    p1.findAllIn(ip).count(p2.findAllIn(_).nonEmpty) > 0
  }

  def supportsSSL(ip: String): Boolean = {
    val p1 = """\[([a-z]*)\]""".r
    val brackets = p1.findAllIn(ip).toArray
    val noBrackets = p1.replaceAllIn(ip, "-").split("-")
    noBrackets.count { sip =>
      val babs = extractABAs(sip).map(aba => aba.slice(1, 2) + aba.take(1) + aba.slice(1, 2))
      babs.count { bab =>
        brackets.count(_.contains(bab)) > 0
      } > 0
    } > 0
  }

  def extractABAs(sip: String): Array[String] = {
    val abaPattern = """(?=(([a-z])(?!\2)[a-z]\2))""".r
    abaPattern.findAllMatchIn(sip).map(_.group(1)).toArray
  }

}
