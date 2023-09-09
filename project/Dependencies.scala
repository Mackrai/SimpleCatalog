import sbt._

object Dependencies {

    object V {
        val http4s = "0.23.23"
        val tapir = "1.7.3"
        val zio = "2.0.16"
        val zioConfig = "4.0.0-RC14"
    }

    val http4s =
        Seq(
            "org.http4s" %% "http4s-ember-client",
            "org.http4s" %% "http4s-ember-server",
            "org.http4s" %% "http4s-dsl"
        ).map(_ % V.http4s)

    val tapir =
        Seq(
            "com.softwaremill.sttp.tapir" %% "tapir-core",
            "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle",
            "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
            "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio"
    ).map(_ % V.tapir)

    val zio =
        Seq(
            "dev.zio" %% "zio"
        ).map(_ % V.zio)

    val zioConfig = {
        Seq(
            "dev.zio" %% "zio-config",
            "dev.zio" %% "zio-config-typesafe",
            "dev.zio" %% "zio-config-magnolia"
        ).map(_ % V.zioConfig)
    }
}
