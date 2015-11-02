package net.leonini.passwordgrids

import java.io.File
import java.lang.System
import com.typesafe.config.ConfigFactory

object CLI {

  val version = 1.0
  
  def main(args: Array[String]) {
    
    println(f"# PasswordGrids v$version%2.1f")
    
    val alphabet = {
      val f: File = new File(System.getProperty("user.home") + File.separator + ".passwordgrids");
      if (f.exists() && !f.isDirectory()) {
        val cfg = ConfigFactory.parseFile(f)
        if (cfg.hasPath("alphabet"))
          cfg.getString("alphabet")
        else "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
      } else "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
    
    val standardIn = System.console()
    
    print("> master password: ")
    val salt = standardIn.readPassword() mkString ""
    
    print("> identifier: ")
    val identifier = standardIn.readPassword() mkString ""
    
    val d = new GridsDisplay(salt, identifier, alphabet = alphabet)
    print(d)
  }
}
