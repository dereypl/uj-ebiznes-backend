import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.service.ProductService

fun Route.productsRoute() {
    val productService = ProductService()
    route("/products") {
        get {
            val products = productService.getAll()
            call.respond(products)
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val product = productService.getById(id.toInt()) ?: return@get call.respondText(
                "No product with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(product)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (productService.remove(id.toInt())) {
                call.respondText("Product removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}