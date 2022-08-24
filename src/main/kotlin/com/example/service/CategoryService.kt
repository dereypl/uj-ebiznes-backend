package service

import com.example.models.Categories
import com.example.models.Category
import com.thebookofjoel.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class CategoryService {
    suspend fun getAll(): List<Category> = dbQuery {
        Categories.slice(Categories.id, Categories.name).selectAll().map { toCategory(it) }
    }

    suspend fun getById(id: Int): Category? = dbQuery {
        Categories.select { Categories.id eq id }.mapNotNull { toCategory(it) }.singleOrNull()
    }

    private fun toCategory(row: ResultRow): Category =
        Category(
            id = row[Categories.id],
            name = row[Categories.name],
        )
}