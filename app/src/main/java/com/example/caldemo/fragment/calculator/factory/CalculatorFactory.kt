package com.example.caldemo.fragment.calculator.factory

import com.example.caldemo.domain.Calculator
import com.example.caldemo.helper.CalculatorAction
import com.example.caldemo.helper.CalculatorOperation
import com.example.caldemo.helper.CalculatorState
import javax.inject.Inject

class CalculatorFactory @Inject constructor() : Calculator {
    override fun calculate(state: CalculatorState, action: CalculatorAction): CalculatorState {
        return when (action) {
            is CalculatorAction.Number -> {
                if (state.operation == null) {
                    val updated = if (state.number1 == "0") {
                        action.number.toString()
                    } else {
                        state.number1 + action.number
                    }
                    state.copy(number1 = updated)
                } else {
                    val updated = if (state.number2 == "0") {
                        action.number.toString()
                    } else {
                        state.number2 + action.number
                    }
                    state.copy(number2 = updated)
                }
            }

            is CalculatorAction.Decimal -> {
                if (state.operation == null && !state.number1.contains(".")) {
                    state.copy(number1 = state.number1 + ".")
                } else if (state.operation != null && !state.number2.contains(".")) {
                    state.copy(number2 = state.number2 + ".")
                } else state
            }

            is CalculatorAction.Clear -> CalculatorState(number1 = "0")
            is CalculatorAction.Delete -> {
                when {
                    state.number2.isNotEmpty() -> state.copy(number2 = state.number2.dropLast(1))
                    state.operation != null -> state.copy(operation = null)
                    state.number1.isNotEmpty() -> {
                        val updated = state.number1.dropLast(1).ifEmpty { "0" }
                        state.copy(number1 = updated)
                    }

                    else -> state
                }
            }

            is CalculatorAction.Operation -> {
                if (state.number1.isNotEmpty() && state.operation == null) {
                    state.copy(operation = action.operation)
                } else state
            }

            is CalculatorAction.Calculate -> {
                val n1 = state.number1.toDoubleOrNull()
                val n2 = state.number2.toDoubleOrNull()
                if (n1 != null && n2 != null) {
                    val result = when (state.operation) {
                        CalculatorOperation.Add -> n1 + n2
                        CalculatorOperation.Subtract -> n1 - n2
                        CalculatorOperation.Multiply -> n1 * n2
                        CalculatorOperation.Divide -> n1 / n2
                        null -> return state
                    }
                    state.copy(
                        number1 = result.toString(),
                        number2 = "",
                        operation = null
                    )
                } else state
            }
        }
    }
}