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
    
    val sampleChartData2 = ProductChartData(
        productId = 2,
        productName = "된장",
        startYear = 2020,
        endYear = 2024,
        inflationRate = 8.7,
        annualData = listOf(
            AnnualPriceData(2020, 2800),
            AnnualPriceData(2021, 2900),
            AnnualPriceData(2022, 3100),
            AnnualPriceData(2023, 3300),
            AnnualPriceData(2024, 3600)
        )
    )
    
    val sampleChartData3 = ProductChartData(
        productId = 3,
        productName = "고추장",
        startYear = 2020,
        endYear = 2024,
        inflationRate = 12.1,
        annualData = listOf(
            AnnualPriceData(2020, 4200),
            AnnualPriceData(2021, 4500),
            AnnualPriceData(2022, 4900),
            AnnualPriceData(2023, 5400),
            AnnualPriceData(2024, 5800)
        )
    )
}
