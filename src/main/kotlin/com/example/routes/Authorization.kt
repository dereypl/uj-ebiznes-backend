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
import com.example.service.UserService


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