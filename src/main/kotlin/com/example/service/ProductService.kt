package service

import com.example.models.Product
import com.example.models.Products
import com.thebookofjoel.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ProductService {
    suspend fun getAll(): List<Product> = dbQuery {
        Products.slice(Products.id, Products.name, Products.price, Products.categoryId).selectAll().map { toProduct(it) }
    }

    suspend fun getProductById(id: Int): Product? = dbQuery {
        Products.select { Products.id eq id }.mapNotNull { toProduct(it) }.singleOrNull()
    }

    private fun toProduct(row: ResultRow): Product =
        Product(
            id = row[Products.id],
            name = row[Products.name],
            price = row[Products.price],
            categoryId = row[Products.categoryId]
        )
//TODO:
//    suspend fun createUser(n: String, id: String) = dbQuery {
//        Users.insert {
//            it[name] = n
//            it[oauthId] = id
//        }
//    }

}