package com.example.plugins

import com.example.routes.categoriesRoute
import com.example.routes.orderRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import productsRoute
import com.example.service.UserService
import signIn

fun Application.configureRouting(userService: UserService, simpleJWT: SimpleJWT) {
    routing {
        productsRoute()
        categoriesRoute()
        orderRoutes()
        signIn(userService, simpleJWT)
    }
}
