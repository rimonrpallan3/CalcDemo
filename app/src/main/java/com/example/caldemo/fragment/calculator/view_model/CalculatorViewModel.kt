package com.example.caldemo.fragment.calculator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.caldemo.domain.Calculator
import com.example.caldemo.helper.CalculatorAction
import com.example.caldemo.helper.CalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculator: Calculator
) : ViewModel() {

    private val _state = MutableLiveData(CalculatorState())
    val state: LiveData<CalculatorState> = _state

    fun onAction(action: CalculatorAction) {
        val currentState = _state.value ?: CalculatorState()
        _state.value = calculator.calculate(currentState, action)
    }
}