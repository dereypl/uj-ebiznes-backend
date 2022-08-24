package com.example.service
import Order
import Orders
import com.example.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime

class OrderService {
    suspend fun getAll(): List<Order> = dbQuery {
        Orders.slice(Orders.id, Orders.userId).selectAll().map { toOrder(it) }
    }

    suspend fun getById(id: Int): Order? = dbQuery {
        Orders.select { Orders.id eq id }.mapNotNull { toOrder(it) }.singleOrNull()
    }

    suspend fun create(uId: EntityID<Int>) = dbQuery {
        Orders.insert {
            it[userId] = uId
        }
    }
    suspend fun remove(id: Int): Boolean {
        //TODO:
        return true
    }

    private fun toOrder(row: ResultRow): Order =
        Order(
            id = row[Orders.id],
            userId = row[Orders.userId],
            date = DateTime(),
        )
}