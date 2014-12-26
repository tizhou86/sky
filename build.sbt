import sbt._
import Keys._

libraryDependencies in ThisBuild ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.specs2" %% "specs2-core" % "2.3.11" % "test"
  )
}

lazy val sky = project.in(file(".")).aggregate(core, client, server, routing, protocol, navigator)

lazy val protocol = project

lazy val routing = project.dependsOn(protocol)

lazy val core = project.dependsOn(routing)

lazy val client = project.dependsOn(core, protocol)

lazy val server = project.dependsOn(core)

lazy val navigator = project.dependsOn(client)
