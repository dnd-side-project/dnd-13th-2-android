package com.side.dnd.feature.price_rank.data.repository

import com.side.dnd.feature.price_rank.data.api.PriceRankApi
import com.side.dnd.feature.price_rank.model.ProductRanking
import com.side.dnd.feature.price_rank.model.ProductChartData
import javax.inject.Inject

class ProductPriceRepository @Inject constructor(
    private val priceRankApi: PriceRankApi
) {
    suspend fun getProductRanking(productId: Int): Result<ProductRanking> {
        return try {
            val result = priceRankApi.getProductRanking(productId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductTrend(productId: Int): Result<ProductChartData> {
        return try {
            val result = priceRankApi.getProductTrends(productId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
