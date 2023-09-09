package io.simple.catalog

import config.AppConfig
import repository.CatalogRepo
import service.CatalogService

import zio._
import zio.config.typesafe.TypesafeConfigProvider

object Main extends ZIOAppDefault {
    override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
        Runtime.setConfigProvider(TypesafeConfigProvider.fromResourcePath())

    val catalogItemTable: scala.collection.mutable.Map[String, model.CatalogItem] = scala.collection.mutable.Map.empty

    private val app =
        for {
            appConfig <- ZIO.service[AppConfig]
            _ <- Server.run(appConfig)
        } yield ()

    override def run = {
        app.provide(
            AppConfig.live,
            CatalogService.live,
            CatalogRepo.live
        ).exitCode
    }
}
