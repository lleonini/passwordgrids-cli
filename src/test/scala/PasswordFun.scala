package net.leonini.passwordgrid

import org.scalatest._

class PasswordFun extends FlatSpec {
  def randomString(n: Int): String = {
    n match {
      case 1 => util.Random.nextPrintableChar.toString
      case _ => util.Random.nextPrintableChar.toString ++ randomString(n-1).toString
    }
  }
  "A 4x4 password grid of ABC" should "equal BCCC CBAB CCBC BBCC" in {
    val g = new Grid("test", "ABC", 4)
    assert(g.toString == "BCCC\nCBAB\nCCBC\nBBCC")
  }
  "A 4x4 password grid of 0-9A-Z" should "equal M2W5 2VF1 QQYB DAH2" in {
    val g = new Grid("test", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", 4)
    assert(g.toString == "M2W5\n2VF1\nQQYB\nDAH2")
  }
  "A 8x8 password grid length" should "be equal 64 + 7" in {
    val g = new Grid("test", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ", 8)
    assert(g.toString.length == 64 + 7)
  }
}
