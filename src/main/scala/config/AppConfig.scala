package io.simple.catalog
package config

import zio.{Config, ZIO, ZLayer}
import zio.config.magnolia.deriveConfig

case class AppConfig(port: Int, host: String)

object AppConfig {
    private val config: Config[AppConfig] =
        deriveConfig[AppConfig].nested("AppConfig")

    val live: ZLayer[Any, Config.Error, AppConfig] =
        ZLayer.fromZIO(
            ZIO.config[AppConfig](config).map { c =>
                AppConfig(port = c.port, host = c.host)
            }
        )
}
