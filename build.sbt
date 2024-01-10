ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "Event-Streams"
  )

libraryDependencies ++= Seq(

  // Cats & Cats Effect
  "org.typelevel"         %% "cats-core"        % "2.10.0",
  "org.typelevel"         %% "cats-effect"      % "3.6-0142603",
  "org.typelevel"         %% "cats-mtl"         % "1.4.0",

  // Endless4s
  "io.github.endless4s"   %% "endless-core"     % "0.28.0",
)
