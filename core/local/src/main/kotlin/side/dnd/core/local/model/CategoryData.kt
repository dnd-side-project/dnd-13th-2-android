package side.dnd.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CategoryResponse(
    val categoryName: String,
    val categoryCode: String,
    val items: List<ItemData>
)

data class ItemData(
    val itemName: String,
    val productId: Int? = null,
    val kinds: List<KindData>? = null
)

data class KindData(
    val productId: Int,
    val kindName: String
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val categoryCode: String,
    val categoryName: String
)

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val categoryCode: String,
    val itemName: String,
    val productId: Int?,
    val hasKinds: Boolean
)

@Entity(tableName = "kinds")
data class KindEntity(
    @PrimaryKey val id: Int,
    val itemId: Int,
    val productId: Int,
    val kindName: String
)
