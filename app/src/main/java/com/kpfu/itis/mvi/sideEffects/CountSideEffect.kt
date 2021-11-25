package com.kpfu.itis.mvi.sideEffects

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.kpfu.itis.mvi.Calculation
import com.kpfu.itis.mvi.IndexField
import com.kpfu.itis.mvi.store.MainActivityAction
import com.kpfu.itis.mvi.store.MainActivityState
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType

typealias MainActivitySideEffect = SideEffect<MainActivityState, MainActivityAction>

class CountSideEffect(
    private val calc: Calculation,
) : MainActivitySideEffect {

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.AddNewCount>()
            .switchMap { action ->
                calcAndUpdate(action.newCount, action.index, state())
                    .map<MainActivityAction> { calculatedValues ->
                        MainActivityAction.CalculationFinished(
                            calculatedValues[0],
                            calculatedValues[1],
                            calculatedValues[2]
                        )
                    }
                    .toObservable()
                    .startWith(MainActivityAction.CalculationStarted)
            }
    }

    private fun calcAndUpdate(
        newCount: Int?,
        index: IndexField,
        state: MainActivityState
    ): Single<MutableList<Int?>> {
        with(state) {
            when (index) {
                IndexField.FIRST_FIELD -> firstCount = newCount
                IndexField.SECOND_FIELD -> secondCount = newCount
                IndexField.THIRD_FIELD -> thirdCount = newCount
            }
        }
        if (index == state.lastIndex) {
            return calc.calculateValue(state)
        }
        state.prevLastIndex = state.lastIndex
        state.lastIndex = index
        return calc.calculateValue(state)
    }
}