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
            RegionRanking(3, "강원", 4500),
            RegionRanking(4, "인천", 4200),
            RegionRanking(5, "광주", 4000),
            RegionRanking(6, "대전", 3800),
            RegionRanking(7, "울산", 3600),
            RegionRanking(8, "세종", 3400),
            RegionRanking(9, "경기", 3200),
            RegionRanking(10, "강원", 3000),
            RegionRanking(11, "충북", 2800),
            RegionRanking(12, "충남", 2600),
            RegionRanking(13, "전북", 2400),
            RegionRanking(14, "전남", 2200),
            RegionRanking(15, "경북", 2000),
            RegionRanking(16, "경남", 1800),
            RegionRanking(17, "제주", 1600)
        )
    )
}
