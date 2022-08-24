import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import com.example.models.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

object Orders : IntIdTable() {
    val date = DateTime("date")
    val userId = reference("user_id", Users)
}

object OrderEntityIdSerializer : KSerializer<EntityID<Int>> {
    override val descriptor = PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder): EntityID<Int> {
        return EntityID(decoder.decodeInt(), Orders)
    }
    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }
}

object DateSerializer : KSerializer<DateTime> {
    override val descriptor = PrimitiveSerialDescriptor("DateTime", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: DateTime) = encoder.encodeLong(value.millis)
    override fun deserialize(decoder: Decoder): DateTime = DateTime(decoder.decodeLong())
}

@Serializable
data class Order(
    @Serializable(with = OrderEntityIdSerializer::class)
    val id: EntityID<Int>,
    @Serializable(with = DateSerializer::class)
    val date: DateTime,
    @Serializable(with = OrderEntityIdSerializer::class)
    val userId: EntityID<Int>,
)