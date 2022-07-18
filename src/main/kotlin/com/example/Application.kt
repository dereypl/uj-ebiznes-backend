package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.thebookofjoel.DatabaseFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import service.UserService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.sessions.*
import kotlinx.serialization.*
import io.ktor.serialization.kotlinx.json.*

val applicationHttpClient = HttpClient(CIO) {
//    install(ContentNegotiation) {
//        json()
//    }
}

data class UserSession(val token: String)
@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)


fun main(args: Array<String> ): Unit = io.ktor.server.netty.EngineMain.main(args)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(Sessions) {
        cookie<UserSession>("user_session")
    }
    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = applicationHttpClient
        }
    }



    val userService = UserService()
    val simpleJwt = SimpleJWT(environment.config.property("jwt.secret").getString())

    DatabaseFactory.init()

    embeddedServer(Netty, port = 8083, host = "0.0.0.0") {
        configureSecurity(simpleJwt)
        configureSerialization()
        configureRouting(userService, simpleJwt)
    }.start(wait = true)
}





//data class LoginRegister(val email: String, val password: String)


