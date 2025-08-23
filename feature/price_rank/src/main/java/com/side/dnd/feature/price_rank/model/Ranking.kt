package com.side.dnd.feature.price_rank.model

data class ProductRanking(
    val productId: Int,
    val productName: String,
    val surveyDate: String,
    val ranking: List<RegionRanking>
)

data class RegionRanking(
    val rank: Int,
    val regionName: String,
    val price: Int
)

object MockProductRanking {
    val sampleProductRanking = ProductRanking(
        productId = 1,
        productName = "",
        surveyDate = "",
        ranking = listOf(
            RegionRanking(1, "울산", 1000),
            RegionRanking(2, "충남", 4800),
            RegionRanking(3, "강원", 4500)
        )
    )
}
