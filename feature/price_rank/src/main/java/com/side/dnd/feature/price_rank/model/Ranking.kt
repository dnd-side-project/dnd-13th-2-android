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
        productName = "김치",
        surveyDate = "2024-12-01",
        ranking = listOf(
            RegionRanking(1, "서울", 5200),
            RegionRanking(2, "부산", 4800),
            RegionRanking(3, "대구", 4500),
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
    
    val sampleProductRanking2 = ProductRanking(
        productId = 2,
        productName = "된장",
        surveyDate = "2024-12-01",
        ranking = listOf(
            RegionRanking(1, "서울", 3600),
            RegionRanking(2, "부산", 3400),
            RegionRanking(3, "대구", 3200),
            RegionRanking(4, "인천", 3000),
            RegionRanking(5, "광주", 2800),
            RegionRanking(6, "대전", 2600),
            RegionRanking(7, "울산", 2400),
            RegionRanking(8, "세종", 2200),
            RegionRanking(9, "경기", 2000),
            RegionRanking(10, "강원", 1800),
            RegionRanking(11, "충북", 1600),
            RegionRanking(12, "충남", 1400),
            RegionRanking(13, "전북", 1200),
            RegionRanking(14, "전남", 1000),
            RegionRanking(15, "경북", 800),
            RegionRanking(16, "경남", 600),
            RegionRanking(17, "제주", 400)
        )
    )
    
    val sampleProductRanking3 = ProductRanking(
        productId = 3,
        productName = "고추장",
        surveyDate = "2024-12-01",
        ranking = listOf(
            RegionRanking(1, "서울", 5800),
            RegionRanking(2, "부산", 5400),
            RegionRanking(3, "대구", 5000),
            RegionRanking(4, "인천", 4600),
            RegionRanking(5, "광주", 4200),
            RegionRanking(6, "대전", 3800),
            RegionRanking(7, "울산", 3400),
            RegionRanking(8, "세종", 3000),
            RegionRanking(9, "경기", 2600),
            RegionRanking(10, "강원", 2200),
            RegionRanking(11, "충북", 1800),
            RegionRanking(12, "충남", 1400),
            RegionRanking(13, "전북", 1000),
            RegionRanking(14, "전남", 800),
            RegionRanking(15, "경북", 600),
            RegionRanking(16, "경남", 400),
            RegionRanking(17, "제주", 200)
        )
    )
}
