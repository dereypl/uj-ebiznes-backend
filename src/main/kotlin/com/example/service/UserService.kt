package service

import com.thebookofjoel.DatabaseFactory.dbQuery
import models.User
import models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class UserService {
    suspend fun getAllUsers(): List<User> = dbQuery {
        Users.slice(Users.id, Users.name, Users.oauthId).selectAll().map { toUser(it) }
    }

    suspend fun getUserByOauthId(oauthId: String): User? = dbQuery {
        Users.select { Users.oauthId eq oauthId }.mapNotNull { toUser(it) }.singleOrNull()
    }

    suspend fun getById(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }.mapNotNull { toUser(it) }.singleOrNull()
    }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            name = row[Users.name],
            oauthId = row[Users.oauthId],
        )

    suspend fun createUser(n: String, id: String) = dbQuery {
        Users.insert {
            it[name] = n
            it[oauthId] = id
        }
    }

}