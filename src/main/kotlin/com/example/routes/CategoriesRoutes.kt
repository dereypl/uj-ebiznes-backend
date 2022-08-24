package com.example.routes

import com.example.models.Category
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.service.CategoryService

fun Route.categoriesRoute() {
    val categoryService = CategoryService()
    route("/categories") {
        get {
            val categories = categoryService.getAll()
            call.respond(categories)
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val category = categoryService.getById(id.toInt()) ?: return@get call.respondText(
                "No category with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(category)
        }

        post {
            val category = call.receive<Category>()
            categoryService.create(category.name)

            call.respondText("Category stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (categoryService.remove(id.toInt())) {
                call.respondText("Category removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}