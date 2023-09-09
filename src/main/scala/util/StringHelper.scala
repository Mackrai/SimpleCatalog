package io.simple.catalog
package util

import java.util.UUID

object StringHelper {
    def randomUuidStr: String = UUID.randomUUID().toString

    val uuidExample: String = randomUuidStr
}
