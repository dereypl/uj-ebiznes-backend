package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import service.CategoryService

fun Route.categoriesRoute() {
    val categoryService = CategoryService()
    route("/categories") {
        get {
            val categories = categoryService.getAll()
            call.respond(categories)
        }
    }
}