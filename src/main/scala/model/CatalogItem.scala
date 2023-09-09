package io.simple.catalog
package model

import util.StringHelper.randomUuidStr

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

case class CatalogItem(id: String = randomUuidStr,
                       title: String,
                       description: Option[String])

object CatalogItem {
    implicit val decoder: Decoder[CatalogItem] = deriveDecoder[CatalogItem]
    implicit val encoder: Encoder[CatalogItem] = deriveEncoder[CatalogItem]

    implicit val schema: Schema[CatalogItem] = Schema.derived

    val example: CatalogItem =
        CatalogItem(title = "Some title", description = Some("Some description"))
}
