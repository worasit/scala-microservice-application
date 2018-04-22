import Build._
import sbt.Keys._

name := "talents_search"

version := "1.0"

runAll := {
  (run in Compile in `web-app`).partialInput(" 3000").evaluated
  (run in Compile in `so-app`).partialInput(" 5000").evaluated
  (run in Compile in `auth-app`).partialInput(" 5001").evaluated
  (run in Compile in `rank-app`).partialInput(" 5002").evaluated
}

fork in run := true

val runAll = inputKey[Unit]("Runs all sub-projects")

concurrentRestrictions in Global := Seq(
  Tags.customLimit(_ => true)
)