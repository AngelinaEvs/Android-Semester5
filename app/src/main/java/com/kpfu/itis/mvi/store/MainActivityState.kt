package com.kpfu.itis.mvi.store

import com.kpfu.itis.mvi.IndexField

data class MainActivityState(
    val isLoading: Boolean = false,
    var firstCount: Int? = null,
    var secondCount: Int? = null,
    var thirdCount: Int? = null,
    var lastIndex: IndexField = IndexField.UNSPECIFIED_FIELD,
    var prevLastIndex: IndexField = IndexField.UNSPECIFIED_FIELD
)
