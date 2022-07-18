package models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val password: Column<String> = varchar("password", 100)
    val active: Column<Boolean> = bool("active")
}

@Serializable
data class User(
    val id: Int,
    val email: String,
    val active: Boolean? = null,
    val password: String? = null
)

@Serializable
data class NewUser(
    val email: String,
    val password: String
)
