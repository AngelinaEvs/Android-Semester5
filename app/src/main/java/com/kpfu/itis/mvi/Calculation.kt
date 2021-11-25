package com.kpfu.itis.mvi

import com.kpfu.itis.mvi.store.MainActivityState
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class Calculation {

    fun calculateValue(state: MainActivityState): Single<MutableList<Int?>> {
        var res = mutableListOf<Int?>()
        state.apply {
            when (lastIndex) {
                IndexField.FIRST_FIELD -> {
                    res = if (prevLastIndex == IndexField.SECOND_FIELD) {
                        val thirdCount = firstCount?.plus(secondCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    } else {
                        val secondCount = thirdCount?.minus(firstCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    }
                }
                IndexField.SECOND_FIELD -> {
                    res = if (prevLastIndex == IndexField.FIRST_FIELD) {
                        val thirdCount = firstCount?.plus(secondCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    } else {
                        val firstCount = thirdCount?.minus(secondCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    }
                }
                IndexField.THIRD_FIELD -> {
                    res = if (prevLastIndex == IndexField.FIRST_FIELD) {
                        val secondCount = thirdCount?.minus(firstCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    } else {
                        val firstCount = thirdCount?.minus(secondCount ?: 0)
                        setCalculatedValue(firstCount, secondCount, thirdCount)
                    }
                }
            }
        }
        return Single.just(res)
            .delay(5, TimeUnit.SECONDS)
    }

    private fun setCalculatedValue(firstCount: Int?, secondCount: Int?, thirdCount: Int?)
            = mutableListOf(firstCount, secondCount, thirdCount)
}