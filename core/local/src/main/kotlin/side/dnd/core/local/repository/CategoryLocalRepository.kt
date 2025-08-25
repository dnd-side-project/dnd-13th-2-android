package side.dnd.core.local.repository

import side.dnd.core.local.dao.CategoryDao
import side.dnd.core.local.model.CategoryEntity
import side.dnd.core.local.model.ItemEntity
import side.dnd.core.local.model.KindEntity
import side.dnd.core.local.model.CategoryResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryLocalRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    
    suspend fun insertCategoryData(categoryResponses: List<CategoryResponse>) {
        val categories = mutableListOf<CategoryEntity>()
        val items = mutableListOf<ItemEntity>()
        val kinds = mutableListOf<KindEntity>()
        
        var itemIdCounter = 1
        var kindIdCounter = 1
        
        categoryResponses.forEach { categoryResponse ->
            categories.add(
                CategoryEntity(
                    categoryCode = categoryResponse.categoryCode,
                    categoryName = categoryResponse.categoryName
                )
            )
            
            categoryResponse.items.forEach { itemData ->
                val hasKinds = itemData.kinds != null && itemData.kinds.isNotEmpty()
                
                items.add(
                    ItemEntity(
                        id = itemIdCounter,
                        categoryCode = categoryResponse.categoryCode,
                        itemName = itemData.itemName,
                        productId = itemData.productId,
                        hasKinds = hasKinds
                    )
                )
                
                if (hasKinds) {
                    itemData.kinds?.forEach { kindData ->
                        kinds.add(
                            KindEntity(
                                id = kindIdCounter++,
                                itemId = itemIdCounter,
                                productId = kindData.productId,
                                kindName = kindData.kindName
                            )
                        )
                    }
                }
                
                itemIdCounter++
            }
        }
        
        categoryDao.insertCategories(categories)
        categoryDao.insertItems(items)
        categoryDao.insertKinds(kinds)
    }
    
    suspend fun getAllCategories(): List<CategoryEntity> {
        return categoryDao.getAllCategories()
    }
    
    suspend fun getItemsByCategory(categoryCode: String): List<ItemEntity> {
        return categoryDao.getItemsByCategory(categoryCode)
    }
    
    suspend fun getKindsByItem(itemId: Int): List<KindEntity> {
        return categoryDao.getKindsByItem(itemId)
    }
    
    suspend fun searchItems(query: String): List<ItemEntity> {
        return categoryDao.searchItems(query)
    }
    
    suspend fun searchKinds(query: String): List<KindEntity> {
        return categoryDao.searchKinds(query)
    }
    
    suspend fun clearAllData() {
        categoryDao.clearAllData()
    }
}
