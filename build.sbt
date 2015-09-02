import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.license.Apache2_0

organization := "com.slamdata.slamengine"

name := "slamengine-jdbc"

version := "0.1-SNAPSHOT"

headers := Map(
  "scala" -> Apache2_0("2014 - 2015", "SlamData Inc."),
  "java"  -> Apache2_0("2014 - 2015", "SlamData Inc."))

enablePlugins(AutomateHeaderPlugin)

scalaVersion := "2.11.7"

initialize := {
  assert(
    Integer.parseInt(sys.props("java.specification.version").split("\\.")(1))
      >= 7,
    "Java 7 or above required")
}

libraryDependencies ++= Seq(
  "io.argonaut"             %% "argonaut"      % "6.1-M4",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.1",
  "org.specs2"              %% "specs2"        % "2.3.13-scalaz-7.1.0-RC1" % "test"
)

assemblySettings
