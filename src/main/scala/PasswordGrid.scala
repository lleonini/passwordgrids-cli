package net.leonini.passwordgrid

import java.security.MessageDigest

class Grid(val text: String, val alphabet: String, val size: Int) {
  require(alphabet.length > 0, "missing alphabet")
  require(alphabet.length < 256, "too long alphabet")

  val hash: Array[Byte] = {
    val md = MessageDigest.getInstance("SHA-512")
    md.update(text.getBytes("UTF-8"))
    md.digest
  }
  val hashToInt: Array[Int] = hash map (b => b & 0xFF)
  require(hashToInt.length >= size * size, "hash size too small for grid")

  val toChars: Seq[Seq[Char]] = {
    for (line <- 0 to size - 1) yield {
      for (col <- 0 to size - 1) yield {
        val pos = line * size + col
        alphabet.charAt(hashToInt(pos) % alphabet.length)
      }
    }
  }

  override def toString = toChars map { line => line mkString "" } mkString "\n"
}

class GridDisplay(
  val salt: String,
  val identifier: String,
  val grid_lines: Int = 3,
  val grid_cols: Int = 4,
  val alphabet: String = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ",
  val size: Int = 8
) {
  //  Return a seq of all lines with only horizontal spacing
  val flatLines = {
    val display_lines = for (l <- 0 to grid_lines - 1) yield {
      val line_of_grids: Seq[Seq[String]] =
        (0 to grid_cols - 1) map { c => {
          val pos = l * grid_lines + c
          val text = salt + "-" + pos + "-" + identifier
          formatLines(new Grid(text, alphabet, size))
        }
        }
      line_of_grids.reduceLeft((a, b) => a zip b map (c => c._1 + "    " + c._2))
    }
    display_lines.flatten
  }

  // Group chars 2 by 2
  def formatLines(grid: Grid): Seq[String] = {
    grid.toChars map {
      line => {
        var pos = 0
        line map {
          c => {
            pos += 1
            if ((pos - 1) % 2 == 0 || pos >= size) c else c + "  "
          }
        } mkString ""
      }
    }
  }

  override def toString = {
    var tmp = "\n"
    for (l <- 0 to flatLines.length - 1) {
      tmp = tmp + flatLines(l) + "\n"
      if ((l + 1) % 2 == 0) tmp = tmp + "\n"
      if ((l + 1) % size == 0) tmp = tmp + "\n"
    }
    tmp.dropRight(1)
  }
}
