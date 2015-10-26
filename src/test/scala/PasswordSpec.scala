package net.leonini.passwordgrid

import org.scalatest._

class PasswordSpec extends FlatSpec with Matchers {
  def randomString(n: Int): String = {
    n match {
      case 1 => util.Random.nextPrintableChar.toString
      case _ => util.Random.nextPrintableChar.toString ++ randomString(n-1).toString
    }
  }
  "A Grid" should "throw IllegalArgumentException if alphabet is empty" in {
    a [IllegalArgumentException] should be thrownBy {
      val g = new Grid("test", "", 0)
    } 
  }
  "A Grid" should "throw IllegalArgumentException if alphabet is too long" in {
    a [IllegalArgumentException] should be thrownBy {
      val g = new Grid("test", randomString(256), 0)
    } 
  }
  "A Grid" should "throw IllegalArgumentException if hash size is too small for grid size" in {
    a [IllegalArgumentException] should be thrownBy {
      val g = new Grid("test", randomString(255), 9)
    } 
  }
}
