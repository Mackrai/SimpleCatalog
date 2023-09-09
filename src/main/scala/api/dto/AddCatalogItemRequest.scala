package io.simple.catalog
package api.dto

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

case class AddCatalogItemRequest(title: String,
                                 description: Option[String])

object AddCatalogItemRequest {
    implicit val decoder: Decoder[AddCatalogItemRequest] = deriveDecoder[AddCatalogItemRequest]
    implicit val encoder: Encoder[AddCatalogItemRequest] = deriveEncoder[AddCatalogItemRequest]
    implicit val schema: Schema[AddCatalogItemRequest] = Schema.derived

    val example =
        AddCatalogItemRequest("Catalog_item_A", Some("Optional description"))
}
