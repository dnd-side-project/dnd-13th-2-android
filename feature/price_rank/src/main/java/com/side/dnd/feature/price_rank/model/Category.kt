package com.side.dnd.feature.price_rank.model

data class Category(
    val categoryName: String,
    val categoryCode: String,
    val items: List<Item>
)