package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntIdTable

object Categories : IntIdTable() {
    val name = varchar("name", 100)
}

@Serializable
data class Category(
    val id: Int,
    val name: String,
)