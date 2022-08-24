package com.example.plugins

import com.example.routes.categoriesRoute
import com.example.routes.orderRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import productsRoute
import com.example.service.UserService
import io.ktor.server.response.*
import signIn

fun Application.configureRouting(userService: UserService, simpleJWT: SimpleJWT) {
    routing {
        get("/") {
            call.respondText("Project: uj-ebiznes-backend is up and running!")
        }
        productsRoute()
        categoriesRoute()
        orderRoutes()
        signIn(userService, simpleJWT)
    }
}
