package com.example.plugins

//import customerRouting
import getOrderRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import listOrdersRoute
import service.UserService
import signIn
import signup
import totalizeOrderRoute

fun Application.configureRouting(userService: UserService, simpleJWT: SimpleJWT) {
    routing {
//        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
        signIn(userService, simpleJWT)
        signup(userService, simpleJWT)
    }
}
