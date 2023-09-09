ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
    .settings(
        name := "SimpleCatalog",
        idePackagePrefix := Some("io.simple.catalog"),
        libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.7" % Runtime,
        libraryDependencies ++=
            Dependencies.http4s ++
                Dependencies.tapir ++
                Dependencies.zio ++
                Dependencies.zioConfig,
        addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
        addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
        scalacOptions ++= Seq(
            "-feature",
            "-deprecation",
            "-unchecked",
            "-language:postfixOps",
            "-Ymacro-annotations"
        )
    )
