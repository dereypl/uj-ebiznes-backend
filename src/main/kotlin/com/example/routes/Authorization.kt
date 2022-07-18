import com.example.plugins.SimpleJWT
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.NewUser
import org.mindrot.jbcrypt.BCrypt
import service.UserService
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.server.auth.*

fun Route.signIn(userService: UserService, simpleJwt: SimpleJWT) {
    route("/signin") {
        post {
            val post = call.receive<NewUser>()

            val user = userService.getUserByEmail(post.email)
            if (user == null || !BCrypt.checkpw(post.password, user.password)) {
                error("Invalid Credentials")
            }

            call.respond(mapOf("token" to simpleJwt.sign(user.email)))
        }
    }
}

fun Route.signup(userService: UserService, simpleJwt: SimpleJWT) {
    route("/signup") {
        post {
            val user = call.receive<NewUser>()

            val created = userService.createUser(user)
            call.respond(created)
        }
    }
    authenticate {
        get("/user" ) {
            call.respond(userService.getAllUsers())
            val principal = call.principal<io.ktor.server.auth.UserIdPrincipal>() ?: error("No principal decoded")
            val userEmail = principal.name
            val user = userService.getUserByEmail(userEmail)?: error("user not found")
            if (!user.active!!) {
                error("user not active")
            }
            call.respond(userService.getAllUsers())
        }
    }
}