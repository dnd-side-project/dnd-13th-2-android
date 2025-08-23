package com.side.dnd.feature.price_rank.model

data class ProductChartData(
    val productId: Int,
    val productName: String,
    val startYear: Int,
    val endYear: Int,
    val inflationRate: Double,
    val annualData: List<AnnualPriceData>
)

data class AnnualPriceData(
    val year: Int,
    val averagePrice: Int
)

object MockProductChartData {
    val sampleChartData = ProductChartData(
        productId = 1,
        productName = "김치",
        startYear = 2020,
        endYear = 2024,
        inflationRate = 15.3,
        annualData = listOf(
            AnnualPriceData(2020, 3500),
            AnnualPriceData(2021, 3800),
            AnnualPriceData(2022, 4200),
            AnnualPriceData(2023, 4800),
            AnnualPriceData(2024, 5200)
        )
    )
}
