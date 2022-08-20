package models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val oauthId: Column<String> = varchar("oauth_id", 100)
}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val oauthId: String,
)
