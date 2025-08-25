package side.dnd.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import side.dnd.core.local.model.CategoryEntity
import side.dnd.core.local.model.ItemEntity
import side.dnd.core.local.model.KindEntity

@Dao
interface CategoryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKinds(kinds: List<KindEntity>)
    
    @Query("SELECT * FROM categories ORDER BY categoryName")
    suspend fun getAllCategories(): List<CategoryEntity>
    
    @Query("SELECT * FROM items WHERE categoryCode = :categoryCode ORDER BY itemName")
    suspend fun getItemsByCategory(categoryCode: String): List<ItemEntity>
    
    @Query("SELECT * FROM kinds WHERE itemId = :itemId ORDER BY kindName")
    suspend fun getKindsByItem(itemId: Int): List<KindEntity>
    
    @Query("SELECT * FROM items WHERE itemName LIKE '%' || :query || '%' ORDER BY itemName")
    suspend fun searchItems(query: String): List<ItemEntity>
    
    @Query("SELECT * FROM kinds WHERE kindName LIKE '%' || :query || '%' ORDER BY kindName")
    suspend fun searchKinds(query: String): List<KindEntity>
    
    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
    
    @Query("DELETE FROM items")
    suspend fun deleteAllItems()
    
    @Query("DELETE FROM kinds")
    suspend fun deleteAllKinds()
    
    @Transaction
    suspend fun clearAllData() {
        deleteAllCategories()
        deleteAllItems()
        deleteAllKinds()
    }
}
