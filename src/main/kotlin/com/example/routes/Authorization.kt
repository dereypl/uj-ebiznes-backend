import com.example.plugins.SimpleJWT
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import service.UserService


@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)


val httC = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

fun Route.signIn(userService: UserService, simpleJwt: SimpleJWT) {
//    route("/signin") {
//        post {
//            val post = call.receive<NewUser>()
//            val user = userService.getUserByEmail(post.email)
//
//            if (user == null || !BCrypt.checkpw(post.password, user.password)) {
//                error("Invalid Credentials")
//            }
//
//            call.respond(mapOf("token" to simpleJwt.sign(user.email)))
//        }
//    }

    authenticate("auth-oauth-google") {
        get("/login") {}
        get("/callback") {
            val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            print(principal)
            val userInfo: UserInfo = httC.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${principal?.accessToken.toString()}")
                }
            }.body()

            if(userService.getUserByOauthId(userInfo.id) == null){
                userService.createUser(userInfo.name, userInfo.id)
            }

            val token = simpleJwt.sign(userInfo.name)
            call.response.cookies.append(Cookie("jwt-token", token))
            call.response.headers.append("Authorization", "Bearer $token")
            call.respondRedirect("https://uj-ebiznes-frontend.azurewebsites.net")
        }
    }
}

fun Route.signup(userService: UserService, simpleJwt: SimpleJWT) {
//    route("/signup") {
//        post {
//            val user = call.receive<NewUser>()
//
//            val created = userService.createUser(user)
//            call.respond(created)
//        }
//    }
//
//    authenticate("auth-oauth-google") {
//        get("/login") {
//            // Redirects to 'authorizeUrl' automatically
//        }
//    }
//
//    authenticate("auth-oauth-google") {
//        get("/login") {
//            // Redirects to 'authorizeUrl' automatically
//        }
//
//        get("/callback") {
//            val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
//            call.sessions.set(UserSession(principal?.accessToken.toString()))
//            call.respondRedirect("/hello")
//        }
//    }
//
//    authenticate {
//        get("/user") {
////            call.respond(userService.getAllUsers())
//            val principal = call.principal<io.ktor.server.auth.UserIdPrincipal>() ?: error("No principal decoded")
//            val userEmail = principal.name
//            val user = userService.getUserByEmail(userEmail) ?: error("user not found")
//
////            print(user.active)
////            if (!user.active!!) {
////                error("user not active")
////            }
//            call.respond(user)
//        }
//    }
}