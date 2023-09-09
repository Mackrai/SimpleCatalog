package io.simple.catalog
package api

import api.dto.AddCatalogItemRequest
import model.CatalogItem
import service.CatalogService
import util.StringHelper.uuidExample

import sttp.tapir.json.circe.jsonBody
import sttp.tapir.ztapir._

class CatalogEndpoints[R <: CatalogService] {
    private val catalogEndpoint =
        baseEndpoint.tag("Catalog").in("catalog")

    private val listAll: ZServerEndpoint[R, Any] =
        catalogEndpoint.get
            .in("items")
            .out(jsonBody[List[CatalogItem]])
            .zServerLogic { _ =>
                CatalogService.list.orElseFail("Error occurred")
            }

    private val getItem: ZServerEndpoint[R, Any] =
        catalogEndpoint.get
            .in(path[String]("itemId").example(uuidExample))
            .out(jsonBody[Option[CatalogItem]])
            .zServerLogic { id =>
                CatalogService.getItem(id).orElseFail("Error occurred")
            }

    private val addItem: ZServerEndpoint[R, Any] =
        catalogEndpoint.post
            .in(jsonBody[AddCatalogItemRequest].example(AddCatalogItemRequest.example))
            .out(jsonBody[String])
            .zServerLogic { addCatalogItemRequest =>
                CatalogService.addItem(addCatalogItemRequest).orElseFail("Error occurred")
            }

    private val deleteItem: ZServerEndpoint[R, Any] =
        catalogEndpoint.delete
            .in(path[String]("itemId").example(uuidExample))
            .zServerLogic { id =>
                CatalogService.deleteItem(id).orElseFail("Error occurred")
            }

    val endpoints: List[ZServerEndpoint[R, Any]] =
        List(listAll, getItem, addItem, deleteItem)
}
