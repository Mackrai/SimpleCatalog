package io.simple.catalog
package repository

import Main.catalogItemTable
import api.dto.AddCatalogItemRequest
import model.CatalogItem

import zio._

trait CatalogRepo {
    def list: RIO[Any, List[CatalogItem]]

    def find(id: String): RIO[Any, Option[CatalogItem]]

    def create(addCatalogItemRequest: AddCatalogItemRequest): RIO[Any, String]

    def delete(id: String): RIO[Any, Unit]
}

case class CatalogRepoLive() extends CatalogRepo {
    override def list: RIO[Any, List[CatalogItem]] =
        ZIO.succeed {
            catalogItemTable.values.toList
        }

    override def find(id: String): RIO[Any, Option[CatalogItem]] =
        ZIO.succeed {
            catalogItemTable.get(id)
        }

    override def create(addCatalogItemRequest: AddCatalogItemRequest): RIO[Any, String] =
        ZIO.succeed {
            val newItem =
                CatalogItem(
                    title = addCatalogItemRequest.title,
                    description = addCatalogItemRequest.description
                )

            catalogItemTable.addOne(newItem.id, newItem)
            newItem.id
        }

    override def delete(id: String): RIO[Any, Unit] =
        ZIO.succeed {
            catalogItemTable.remove(id)
        }
}

object CatalogRepo {
    val live: ZLayer[Any, Throwable, CatalogRepoLive] =
        ZLayer.succeed(CatalogRepoLive())
}
