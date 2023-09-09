package io.simple.catalog

import sttp.tapir._
import sttp.tapir.json.circe.jsonBody

package object api {
    val baseEndpoint: Endpoint[Unit, Unit, String, Unit, Any] =
        endpoint.in("api").errorOut(jsonBody[String])
}
