package com.side.dnd.feature.price_rank.component.find

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.side.dnd.feature.price_rank.component.find.state.FindCategoryUiState
import com.side.dnd.feature.price_rank.component.find.state.Product
import kotlinx.collections.immutable.persistentListOf

class FindCategoryUiStatePreviewParameter : PreviewParameterProvider<FindCategoryUiState> {
    override val values: Sequence<FindCategoryUiState>
        get() = sequenceOf(
            FindCategoryUiState.EMPTY,
            FindCategoryUiState.EMPTY.copy(
                searchQuery = TextFieldState("파"),
                searchResult = persistentListOf(
                    Product(
                        id = 0,
                        name = "양파",
                    ),
                    Product(
                        id = 1,
                        name = "대파",
                    ),
                    Product(
                        id = 2,
                        name = "쪽파",
                    ),
                    Product(
                        id = 3,
                        name = "만파식적",
                    ),
                    Product(
                        id = 4,
                        name = "똠양파국",
                    ),
                    Product(
                        id = 5,
                        name = "파국",
                    ),
                )
            ),
        )
}