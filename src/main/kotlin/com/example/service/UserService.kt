package service

import com.thebookofjoel.DatabaseFactory.dbQuery
import models.NewUser
import models.User
import models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.mindrot.jbcrypt.BCrypt


class UserService {
    suspend fun getAllUsers(): List<User> = dbQuery {
        Users.slice(Users.id, Users.email, Users.active).selectAll().map { toUser(it) }
    }

    suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }.mapNotNull { toUser(it) }.singleOrNull()
    }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            email = row[Users.email],
            active = row[Users.active]
        )


    suspend fun createUser(newUser: NewUser) = dbQuery {
        Users.insert {
            it[email] = newUser.email
            it[password] = BCrypt.hashpw(
                newUser.password, BCrypt.gensalt()
            )
        }
    }

}