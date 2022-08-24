package com.example.plugins

import com.example.routes.categoriesRoute
import com.example.routes.orderRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import productsRoute
import com.example.service.UserService
import signIn
import signup

fun Application.configureRouting(userService: UserService, simpleJWT: SimpleJWT) {
    routing {
        productsRoute()
        categoriesRoute()
        orderRoutes()
        signIn(userService, simpleJWT)
        signup(userService, simpleJWT)
    }
}
