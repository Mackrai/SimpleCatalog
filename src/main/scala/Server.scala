package io.simple.catalog

import api.CatalogEndpoints
import config.AppConfig
import service.CatalogService

import com.comcast.ip4s.{Host, Port}
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zio._
import zio.interop.catz.asyncInstance

object Server {
    type AppEnv = CatalogService

    type AppRIO[A] = RIO[AppEnv, A]

    private val routes: HttpRoutes[AppRIO] = {
        val serverEndpoints =
            new CatalogEndpoints[AppEnv].endpoints

        val docs =
            SwaggerInterpreter().fromServerEndpoints(
                endpoints = serverEndpoints,
                title = "Simple Catalog API",
                version = "1.0"
            )

        ZHttp4sServerInterpreter().from(serverEndpoints ++ docs).toRoutes
    }

    def run(appConfig: AppConfig) =
        for {
            host <- ZIO.from(Host.fromString(appConfig.host))
            port <- ZIO.from(Port.fromInt(appConfig.port))
            _ <-
                EmberServerBuilder
                    .default[AppRIO]
                    .withHost(host)
                    .withPort(port)
                    .withHttpApp(Router("/" -> routes).orNotFound)
                    .build
                    .useForever
        } yield ()
}
