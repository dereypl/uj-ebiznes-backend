package com.example.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable


object Products : IntIdTable() {
    val name = varchar("name", 100)
    val price = double("price")
    val categoryId = reference("category_id", Categories)
}

object ProductEntityIdSerializer : KSerializer<EntityID<Int>> {
    override val descriptor = PrimitiveSerialDescriptor("EntityID", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder): EntityID<Int> {
        return EntityID(decoder.decodeInt(), Products)
    }

    override fun serialize(encoder: Encoder, value: EntityID<Int>) {
        encoder.encodeInt(value.value)
    }
}

@Serializable
data class Product(
    @Serializable(with = ProductEntityIdSerializer::class)
    val id: EntityID<Int>,
    val name: String,
    val price: Double,
    @Serializable(with = CategoryEntityIdSerializer::class)
    val categoryId: EntityID<Int>
)