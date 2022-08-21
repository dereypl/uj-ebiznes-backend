package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntIdTable

object Products : IntIdTable() {
    val name = varchar("name", 100)
    val price = double("price")
    val categoryId = reference("category_id", Categories)
}

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val categoryId: Int,
)