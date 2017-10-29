name := "ratting-radar"

version := "0.1"

scalaVersion := "2.12.3"

enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

libraryDependencies += "io.github.outwatch" %%% "outwatch" % "0.10.2"

val circeVersion = "0.9.0-M1"

libraryDependencies ++= Seq(
  "io.circe" %%% "circe-core",
  "io.circe" %%% "circe-generic",
  "io.circe" %%% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "fr.hmil" %%% "roshttp" % "2.0.2"
libraryDependencies += "org.scala-graph" %% "graph-core" % "1.12.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
