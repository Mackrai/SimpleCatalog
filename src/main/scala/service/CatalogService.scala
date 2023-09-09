package io.simple.catalog
package service

import model.CatalogItem
import repository.CatalogRepo

import io.simple.catalog.api.dto.AddCatalogItemRequest
import zio.{RIO, ZIO, ZLayer}

trait CatalogService {
    def list: RIO[Any, List[CatalogItem]]

    def getItem(id: String): RIO[Any, Option[CatalogItem]]

    def addItem(addCatalogItemRequest: AddCatalogItemRequest): RIO[Any, String]

    def deleteItem(id: String): RIO[Any, Unit]
}

case class CatalogServiceLive(catalogRepo: CatalogRepo) extends CatalogService {
    override def list: RIO[Any, List[CatalogItem]] =
        catalogRepo.list

    override def getItem(id: String): RIO[Any, Option[CatalogItem]] =
        catalogRepo.find(id)

    override def addItem(addCatalogItemRequest: AddCatalogItemRequest): RIO[Any, String] =
        catalogRepo.create(addCatalogItemRequest)

    override def deleteItem(id: String): RIO[Any, Unit] =
        catalogRepo.delete(id)
}

object CatalogService {
    val live: ZLayer[CatalogRepo, Nothing, CatalogService] =
        ZLayer {
            ZIO.service[CatalogRepo].map(CatalogServiceLive)
        }

    def list: RIO[CatalogService, List[CatalogItem]] =
        ZIO.serviceWithZIO[CatalogService](_.list)

    def getItem(id: String): RIO[CatalogService, Option[CatalogItem]] =
        ZIO.serviceWithZIO[CatalogService](_.getItem(id))

    def addItem(addCatalogItemRequest: AddCatalogItemRequest): RIO[CatalogService, String] =
        ZIO.serviceWithZIO[CatalogService](_.addItem(addCatalogItemRequest))

    def deleteItem(id: String): RIO[CatalogService, Unit] =
        ZIO.serviceWithZIO[CatalogService](_.deleteItem(id))
}
