import play.PlayImport.PlayKeys._
import play.PlayImport.PlayKeys.routesImport

name := "ticketing-system"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(SbtWeb)

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7", "-Xlint:unchecked", "-Xlint:deprecation")

libraryDependencies ++= Seq(
  filters,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "com.escalatesoft.subcut" %% "subcut" % "2.1",
  "jp.t2v" %% "play2-auth"      % "0.13.0",
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.3.1" exclude ("org.webjars", "jquery"),
  "com.github.athieriot" %% "specs2-embedmongo" % "0.7.0" % "test"
)


routesImport ++= Seq("java.util.UUID")

addCompilerPlugin("com.escalatesoft.subcut" %% "subcut" % "2.1")

LessKeys.compress in Assets := true

includeFilter in (Assets, LessKeys.less) := "*.less"

// The order of stages is significant. You first want to optimize the files,
// produce digests of them and then produce gzip versions of all resultant assets.
// Don't include RequireJS unless we're using it, else no assets in production :(
pipelineStages := Seq(digest, gzip)

// Prevent docs from being packaged
doc in Compile <<= target.map(_ / "none")

play.PlayImport.PlayKeys.playDefaultPort := 9001
