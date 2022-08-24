package com.example.service

import com.example.models.Product
import com.example.models.Products
import com.example.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ProductService {
    suspend fun getAll(): List<Product> = dbQuery {
        Products.slice(Products.id, Products.name, Products.price, Products.categoryId).selectAll().map { toProduct(it) }
    }

    suspend fun getById(id: Int): Product? = dbQuery {
        Products.select { Products.id eq id }.mapNotNull { toProduct(it) }.singleOrNull()
    }

    suspend fun remove(id: Int): Boolean {
        //TODO:
        return true
    }
    private fun toProduct(row: ResultRow): Product =
        Product(
            id = row[Products.id],
            name = row[Products.name],
            price = row[Products.price],
            categoryId = row[Products.categoryId]
        )
}