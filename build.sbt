lazy val root = (project in file(".")).
  settings(
    name := "passwordgrid-cli",
    version := "1.0"
  )

mainClass in Compile := Some("net.leonini.passwordgrid.CLI")

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)
