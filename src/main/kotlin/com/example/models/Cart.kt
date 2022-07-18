//import kotlinx.serialization.Serializable
//import org.jetbrains.exposed.sql.Column
//import org.jetbrains.exposed.sql.Table
//
//object Carts : Table() {
//    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
//    val email: Column<String> = varchar("email", 100).uniqueIndex()
//    val password: Column<String> = varchar("password", 100)
//    val firstName: Column<String> = varchar("firstName", 100)
//    val lastName: Column<String> = varchar("lastName", 100)
//}
//
//@Serializable
//data class Customer(
//    val id: String,
//    val email: String,
//    val firstName: String,
//    val lastName: String,
//)
//
