ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "simple-crawler"
  )

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.9"
val CirceVersion = "0.14.1"
val Slf4jVersion = "1.7.36"
val ScalaLoggingVersion = "3.9.4"
val PureConfigVersion = "0.17.1"
val ScalaTestVersion = "3.2.11"

libraryDependencies ++= Seq(
  //Akka
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,

  //Json
  "io.circe" %% "circe-core" % CirceVersion,
  "io.circe" %% "circe-parser" % CirceVersion,

  //Logging
  "org.slf4j" % "slf4j-simple" % Slf4jVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % ScalaLoggingVersion,

  //Hocon
  "com.github.pureconfig" %% "pureconfig" % PureConfigVersion,

  //Test
  "org.scalatest" %% "scalatest" % ScalaTestVersion % "test"
)
