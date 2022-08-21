import kotlinx.serialization.Serializable
import models.Users
import org.jetbrains.exposed.dao.IntIdTable

object Orders : IntIdTable() {
    val date = date("date")
    val userId = reference("user_id", Users)
    //    val price = double("price")
}


@Serializable
data class Order(
    val id: Int,
    val date: Int,
    val userId: Int,
)