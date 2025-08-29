package com.side.dnd.feature.price_rank.model

data class ProductChartData(
    val productName: String,
    val inflationRate: Double,
    val annualData: List<AnnualPriceData>
)

data class AnnualPriceData(
    val year: Int,
    val averagePrice: Int
)

object MockProductChartData {
    val sampleChartData = ProductChartData(
        productName = "쌀",
        inflationRate = 15.3,
        annualData = listOf(
//            AnnualPriceData(2015,3000),
//            AnnualPriceData(2016,3100),
//            AnnualPriceData(2017,3150),
//            AnnualPriceData(2018,3100),
//            AnnualPriceData(2019,3200),
            AnnualPriceData(2020,3100),
            AnnualPriceData(2021,3100),
            AnnualPriceData(2022,3100),
            AnnualPriceData(2023,3500),
            AnnualPriceData(2024,3100),
            AnnualPriceData(2025,2800),
        )
    )
    val emptyChartData = ProductChartData(
        productName = "",
        inflationRate = 0.0,
        annualData = listOf(
            AnnualPriceData(2020,300),
            AnnualPriceData(2021,100),
            AnnualPriceData(2022,200),
            AnnualPriceData(2023,700),
            AnnualPriceData(2024,1000),
            AnnualPriceData(2025,800)
        )
    )

}
