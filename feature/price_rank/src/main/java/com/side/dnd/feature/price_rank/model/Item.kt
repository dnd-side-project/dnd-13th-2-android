package com.side.dnd.feature.price_rank.model

data class Item(
    val itemName: String,
    val kinds: List<Kind>? = null,
    val productId: Int? = null
)
