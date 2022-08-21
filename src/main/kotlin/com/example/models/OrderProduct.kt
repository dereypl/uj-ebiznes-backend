import com.example.models.Products
import org.jetbrains.exposed.dao.IntIdTable

object OrderProducts : IntIdTable() {
    val productId = reference("product_id", Products)
    val orderId = reference("order_id", Orders)
}