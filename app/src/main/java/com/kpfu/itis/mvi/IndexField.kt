package com.kpfu.itis.mvi

enum class IndexField(val value: Int) {
    UNSPECIFIED_FIELD(0),
    FIRST_FIELD(1),
    SECOND_FIELD(2),
    THIRD_FIELD(3)
}

fun getIndexFieldByValue(value: Int) =
    IndexField.values().find { it.value == value } ?: 1