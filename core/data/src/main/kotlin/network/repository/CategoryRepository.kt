package network.repository

import network.service.EodigoApi
import side.dnd.core.local.model.CategoryResponse
import side.dnd.core.local.repository.CategoryLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val eodigoApi: EodigoApi,
    private val categoryLocalRepository: CategoryLocalRepository
) {
    
    suspend fun fetchAndSaveCategories(): Result<List<CategoryResponse>> {
        return try {
            val categories = eodigoApi.getCategories()
            categoryLocalRepository.insertCategoryData(categories)
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCategoriesFromLocal(): List<CategoryResponse> {
        val categories = categoryLocalRepository.getAllCategories()
        return categories.map { categoryEntity ->
            val items = categoryLocalRepository.getItemsByCategory(categoryEntity.categoryCode)
            val itemsWithKinds = items.map { itemEntity ->
                val kinds = if (itemEntity.hasKinds) {
                    categoryLocalRepository.getKindsByItem(itemEntity.id)
                } else {
                    emptyList()
                }
                
                side.dnd.core.local.model.ItemData(
                    itemName = itemEntity.itemName,
                    productId = itemEntity.productId,
                    kinds = kinds.map { kindEntity ->
                        side.dnd.core.local.model.KindData(
                            productId = kindEntity.productId,
                            kindName = kindEntity.kindName
                        )
                    }.takeIf { it.isNotEmpty() }
                )
            }
            
            CategoryResponse(
                categoryName = categoryEntity.categoryName,
                categoryCode = categoryEntity.categoryCode,
                items = itemsWithKinds
            )
        }
    }
    
    suspend fun searchItems(query: String): List<side.dnd.core.local.model.ItemEntity> {
        return categoryLocalRepository.searchItems(query)
    }
    
    suspend fun searchKinds(query: String): List<side.dnd.core.local.model.KindEntity> {
        return categoryLocalRepository.searchKinds(query)
    }
    
    suspend fun findProductIdByName(productName: String): Int? {
        val items = categoryLocalRepository.searchItems(productName)
        val kinds = categoryLocalRepository.searchKinds(productName)
        
        // 아이템에서 정확히 일치하는 상품 찾기
        val exactItemMatch = items.find { it.itemName == productName }
        if (exactItemMatch?.productId != null) {
            return exactItemMatch.productId
        }
        
        // 품종에서 정확히 일치하는 품종 찾기
        val exactKindMatch = kinds.find { it.kindName == productName }
        if (exactKindMatch != null) {
            return exactKindMatch.productId
        }
        
        // 정확히 일치하지 않으면 첫 번째 결과 반환
        return items.firstOrNull()?.productId ?: kinds.firstOrNull()?.productId
    }
}
