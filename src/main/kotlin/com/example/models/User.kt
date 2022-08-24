package models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users : IntIdTable() {
    val name: Column<String> = varchar("name", 100)
    val oauthId: Column<String> = varchar("oauth_id", 100)
}

object UserEntityIdSerializer : KSerializer<EntityID<Int>> {
    override val descriptor = PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder): EntityID<Int> {
        return EntityID(decoder.decodeInt(), Users)
    }

    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }
}

@Serializable
data class User(
    @Serializable(with = UserEntityIdSerializer::class)
    val id: EntityID<Int>,
    val name: String,
    val oauthId: String,
)
