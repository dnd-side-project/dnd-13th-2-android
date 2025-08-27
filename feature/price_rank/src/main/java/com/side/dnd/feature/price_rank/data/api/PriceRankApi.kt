package com.side.dnd.feature.price_rank.data.api

import com.side.dnd.feature.price_rank.model.ProductRanking
import com.side.dnd.feature.price_rank.model.ProductChartData
import retrofit2.http.GET
import retrofit2.http.Path

interface PriceRankApi {
    
    @GET("products/{productId}/rankings")
    suspend fun getProductRanking(
        @Path("productId") productId: Int
    ): ProductRanking

    @GET("products/{productId}/trends")
    suspend fun getProductTrends(
        @Path("productId") productId: Int
    ): ProductChartData
}
