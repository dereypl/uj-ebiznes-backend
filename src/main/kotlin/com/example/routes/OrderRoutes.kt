package com.example.routes

import Order
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.service.OrderService
import com.example.service.UserService

fun Route.orderRoutes() {
    val orderService = OrderService()
    val userService = UserService()
    route("/orders") {
        get {
            val categories = orderService.getAll()
            call.respond(categories)
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = orderService.getById(id.toInt()) ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)
        }

        post {
            val order = call.receive<Order>()
            print(order)
            val user = userService.getById(1)
            user?.let { it1 -> orderService.create(it1.id) }

            call.respondText("Order stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (orderService.remove(id.toInt())) {
                call.respondText("Order removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}