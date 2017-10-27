name := "ratting-radar"

version := "0.1"

scalaVersion := "2.12.3"

enablePlugins(ScalaJSPlugin)
enablePlugins(DockerPlugin)


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

//jsDependencies += ProvidedJS / "systems.json"

artifactPath in fastOptJS := resourceManaged.value / ((moduleName in fastOptJS).value + "-fastopt.js")

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = (artifactPath in fastOptJS).value
  val artifactTargetPath = s"/usr/share/nginx/html/${artifact.name}"

  new Dockerfile {
    from("nginx")
    add(artifact, artifactTargetPath)
  }
}