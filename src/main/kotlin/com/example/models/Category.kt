package com.example.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable

object Categories : IntIdTable() {
    val name = varchar("name", 100)
}

object CategoryEntityIdSerializer : KSerializer<EntityID<Int>> {
    override val descriptor = PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder): EntityID<Int> {
        return EntityID(decoder.decodeInt(), Categories)
    }

    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }
}

@Serializable
data class Category(
    @Serializable(with = CategoryEntityIdSerializer::class)
    val id: EntityID<Int>,
    val name: String,
)