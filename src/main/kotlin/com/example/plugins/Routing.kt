package com.example.plugins

import customerRouting
import getOrderRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import listOrdersRoute
import totalizeOrderRoute

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
