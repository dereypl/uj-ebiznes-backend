package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.thebookofjoel.DatabaseFactory
import service.UserService
import io.ktor.server.application.*
import io.ktor.server.sessions.*

data class UserSession(val token: String)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(Sessions) {
        cookie<UserSession>("user_session")
    }

    val userService = UserService()
    val simpleJwt = SimpleJWT(environment.config.property("jwt.secret").getString())

    DatabaseFactory.init()

//    install(CORS) {
//        allowHost("*")
//        allowHeader(HttpHeaders.ContentType)
//    }
//
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity(simpleJwt)
        configureSerialization()
        configureRouting(userService, simpleJwt)
    }.start(wait = true)
}


