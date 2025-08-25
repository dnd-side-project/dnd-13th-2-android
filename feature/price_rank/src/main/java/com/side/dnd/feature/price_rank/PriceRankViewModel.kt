package com.side.dnd.feature.price_rank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.side.dnd.feature.price_rank.data.repository.ProductPriceRepository
import com.side.dnd.feature.price_rank.model.NationWideUiState
import com.side.dnd.feature.price_rank.model.ProductRanking
import com.side.dnd.feature.price_rank.model.RegionRanking
import com.side.dnd.feature.price_rank.model.ProductChartData
import com.side.dnd.feature.price_rank.model.AnnualPriceData
import network.repository.CategoryRepository
import side.dnd.core.local.model.ItemEntity
import side.dnd.core.local.model.KindEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.core.local.model.CategoryResponse
import javax.inject.Inject

@HiltViewModel
class PriceRankViewModel @Inject constructor(
    private val productPriceRepository: ProductPriceRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _rankUiState = MutableStateFlow<NationWideUiState>(NationWideUiState.Empty)
    val rankUiState = _rankUiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        initializeCategoryData()
    }

    private fun initializeCategoryData() {
        viewModelScope.launch {
            try {
                Log.d("PriceRankViewModel", "Initializing category data...")
                val result = categoryRepository.fetchAndSaveCategories()
                result.onSuccess { categories ->
                    Log.d("PriceRankViewModel", "Category data initialized successfully: ${categories.size} categories")
                    _categories.value = categories
                }.onFailure { error ->
                    Log.e("PriceRankViewModel", "Failed to initialize category data: ${error.message}")
                    // 로컬에서 데이터 가져오기 시도
                    try {
                        val localCategories = categoryRepository.getCategoriesFromLocal()
                        _categories.value = localCategories
                        Log.d("PriceRankViewModel", "Loaded ${localCategories.size} categories from local database")
                    } catch (e: Exception) {
                        Log.e("PriceRankViewModel", "Failed to load local categories: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("PriceRankViewModel", "Exception during category initialization: ${e.message}")
            }
        }
    }

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            try {
                Log.d("PriceRankViewModel", "Searching for: $query")
                
                val items = categoryRepository.searchItems(query)
                val kinds = categoryRepository.searchKinds(query)
                
                val searchResults = mutableListOf<SearchResult>()
                
                // 아이템 검색 결과 추가
                items.forEach { item ->
                    if (item.productId != null) {
                        searchResults.add(
                            SearchResult(
                                id = item.productId!!,
                                name = item.itemName,
                                type = SearchResultType.ITEM,
                                categoryCode = item.categoryCode
                            )
                        )
                    }
                }
                
                // 품종 검색 결과 추가
                kinds.forEach { kind ->
                    searchResults.add(
                        SearchResult(
                            id = kind.productId,
                            name = kind.kindName,
                            type = SearchResultType.KIND,
                            categoryCode = null
                        )
                    )
                }
                
                _searchResults.value = searchResults.distinctBy { it.id }
                Log.d("PriceRankViewModel", "Search results: ${searchResults.size} items found")
                
            } catch (e: Exception) {
                Log.e("PriceRankViewModel", "Search error: ${e.message}")
                _searchResults.value = emptyList()
            }
        }
    }

    fun selectSearchResult(searchResult: SearchResult) {
        Log.d("PriceRankViewModel", "Selected search result: ${searchResult.name} (ID: ${searchResult.id})")
        loadProductData(searchResult.id, searchResult.name)
        loadProductTrendData(searchResult.id, searchResult.name)
    }

    fun loadProductData(productId: Int, productName: String) {
        Log.d("PriceRankViewModel", "loadProductData called: productId=$productId, productName=$productName")
        viewModelScope.launch {
            try {
                val rankingResult = productPriceRepository.getProductRanking(productId)
                rankingResult.onSuccess { ranking ->
                    Log.d("PriceRankViewModel", "API success: ${ranking.productName}")
                    _rankUiState.update { currentState ->
                        currentState.copy(
                            keyWord = productName,
                            productRanking = ranking,
                            isEmptyKeyword = false
                        )
                    }
                }.onFailure { error ->
                    Log.e("PriceRankViewModel", "API failure: ${error.message}")
                    // Mock 데이터 사용 중단
                }
            } catch (e: Exception) {
                Log.e("PriceRankViewModel", "Exception: ${e.message}")
                // Mock 데이터 사용 중단
            }
        }
    }

    fun loadProductTrendData(productId: Int, productName: String) {
        Log.d("PriceRankViewModel", "loadProductTrendData called: productId=$productId, productName=$productName")
        viewModelScope.launch {
            try {
                val trendResult = productPriceRepository.getProductTrend(productId)
                trendResult.onSuccess { chartData ->
                    Log.d("PriceRankViewModel", "Trend API success")
                    _rankUiState.update { currentState ->
                        currentState.copy(
                            chartData = chartData,
                            isEmptyKeyword = false
                        )
                    }
                }.onFailure { error ->
                    Log.e("PriceRankViewModel", "Trend API failure: ${error.message}")
                    // Mock 데이터 사용 중단
                }
            } catch (e: Exception) {
                Log.e("PriceRankViewModel", "Trend Exception: ${e.message}")
                // Mock 데이터 사용 중단
            }
        }
    }

    fun selectCategory(categoryName: String) {
        Log.d("PriceRankViewModel", "selectCategory called: $categoryName")
        viewModelScope.launch {
            try {
                val productId = categoryRepository.findProductIdByName(categoryName)
                if (productId != null) {
                    Log.d("PriceRankViewModel", "Found productId: $productId for category: $categoryName")
                    loadProductData(productId, categoryName)
                    loadProductTrendData(productId, categoryName)
                } else {
                    Log.e("PriceRankViewModel", "Product not found for category: $categoryName")
                }
            } catch (e: Exception) {
                Log.e("PriceRankViewModel", "Error finding product for category: ${e.message}")
            }
        }
    }
}

data class SearchResult(
    val id: Int,
    val name: String,
    val type: SearchResultType,
    val categoryCode: String?
)

enum class SearchResultType {
    ITEM,
    KIND
}
