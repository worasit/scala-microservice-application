import Dependencies._
import play.sbt.PlayImport._
import play.sbt.PlayScala
import sbt.Keys.{fullClasspath, _}
import sbt.{Build, Project, Resolver, file, project}
import sbt._
import sbtassembly.AssemblyKeys._

/**
  * Contains all projects and settings
  */
object Build extends Build {

  lazy val `talents-search` = (project in file("."))
    .aggregate(
      `web-app`,
      `auth-app`,
      `so-app`,
      `github-app`,
      `rank-app`,
      commons
    )

  lazy val `web-app` = PlayProject("web-app")
    .settings(libraryDependencies ++= Seq(parserCombinator, ws, specs2 % Test, guice, scalaTest))
    .dependsOn(commons)

  lazy val `so-app` = PlayProject("so-app")
    .settings(libraryDependencies ++= Seq(h2, jbcrypt, slick, playSlick, playSlickEvolutions, guice, specs2 % Test))
    .dependsOn(commons)

  lazy val `auth-app` = PlayProject("auth-app")
    .settings(libraryDependencies ++= Seq(h2, jbcrypt, slick, playSlick, playSlickEvolutions, guice, specs2 % Test))
    .dependsOn(commons)

  lazy val `rank-app` = PlayProject("rank-app")
    .settings(libraryDependencies ++= Seq(guice, ws))
    .dependsOn(commons)

  lazy val `github-app` = PlayProject("github-app")
    .settings(libraryDependencies ++= Seq(h2, jbcrypt, slick, playSlick, playSlickEvolutions, guice))
    .dependsOn(commons)

  lazy val commonSettings = Seq(
    organization := "com.nongped.microservices",
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
    scalaVersion := "2.12.2",
    resolvers ++= Seq(
      Resolver.typesafeRepo("releases"),
      "JBoss" at "https://repository.jboss.org/"
    ),
    test in assembly := {}
  )


  lazy val commons = BaseProject("commons")
    .settings(
      libraryDependencies ++= Seq(
        specs2 % Test,
        "com.typesafe.play" %% "play-json" % "2.6.3",
        playTest,
        evolutions
      )
    )

  private def BaseProject(id: String): Project = {
    Project(id, file(id)) settings (commonSettings: _*)
  }

  private def PlayProject(id: String): Project = {
    BaseProject(id) enablePlugins PlayScala settings(
      mainClass in assembly := Some("play.core.server.ProdServerStart"),
      fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)
    )
  }
}
