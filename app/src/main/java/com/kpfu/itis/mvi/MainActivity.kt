package com.kpfu.itis.mvi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.kpfu.itis.mvi.sideEffects.CountSideEffect
import com.kpfu.itis.mvi.store.MainActivityAction
import com.kpfu.itis.mvi.store.MainActivityState
import com.kpfu.itis.mvi.store.MainActivityStore
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val store = MainActivityStore(listOf(CountSideEffect(Calculation())))

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        store.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::render)
        a.doAfterTextChanged { input ->
            calc(input.toString(), IndexField.FIRST_FIELD)
        }
        b.doAfterTextChanged { input ->
            calc(input.toString(), IndexField.SECOND_FIELD)
        }
        c.doAfterTextChanged { input ->
            calc(input.toString(), IndexField.THIRD_FIELD)
        }
    }

    private fun render(state: MainActivityState) {
        state.firstCount?.let {
            if (a.text.toString() != it.toString()) {
                a.setText(it.toString())
            }
        }
        state.secondCount?.let {
            if (b.text.toString() != it.toString()) {
                b.setText(it.toString())
            }
        }
        state.thirdCount?.let {
            if (c.text.toString() != it.toString()) {
                c.setText(it.toString())
            }
        }
        progressBar.isVisible = state.isLoading
    }

    private fun calc(newCount: String, field: IndexField) {
        if (!newCount.isNullOrEmpty()) {
            store.actionRelay.onNext(MainActivityAction.AddNewCount(newCount.toInt(), field))
        }
    }
}