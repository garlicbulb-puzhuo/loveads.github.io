import AssemblyKeys._
import com.typesafe.sbt.SbtStartScript

organization  := "com.loveads.bidder"

version       := "0.1.0"

scalaVersion  := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-target:jvm-1.7")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.7" % "test",
    "org.slf4j"           %   "slf4j-api"     % "1.7.5"
  )
}

Revolver.settings

assemblySettings

org.scalastyle.sbt.ScalastylePlugin.Settings

org.scalastyle.sbt.PluginKeys.config := file("project/scalastyle-config.xml")

