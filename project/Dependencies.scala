import sbt._

/**
  * Contains all dependencies and versions
  */
object Dependencies {
  val slickV = "3.2.1"
  val h2V = "1.4.193"
  val playSlickV = "3.0.1"
  val jbcryptV = "0.4"
  val parserCombinatorV = "1.0.5"

  val slick = "com.typesafe.slick" %% "slick" % slickV
  val slickHikariCP = "com.typesafe.slick" %% "slick-hikaricp" % slickV
  val h2 = "com.h2database" % "h2" % h2V
  val playSlick = "com.typesafe.play" %% "play-slick" % playSlickV
  val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % playSlickV
  val jbcrypt = "org.mindrot" % "jbcrypt" % jbcryptV
  val parserCombinator = "org.scala-lang.modules" % "scala-parser-combinators_2.12" % parserCombinatorV
  val playJson = "com.typesafe.play" %% "play-json" % "2.6.3"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
}
