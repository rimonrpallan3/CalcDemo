package com.example.caldemo.fragment.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.caldemo.R
import com.example.caldemo.databinding.FragmentCalculatorBinding
import com.example.caldemo.fragment.calculator.view_model.CalculatorViewModel
import com.example.caldemo.helper.CalculatorAction
import com.example.caldemo.helper.CalculatorOperation
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val viewModel: CalculatorViewModel by viewModels()
    private lateinit var tvExpression: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvExpression = view.findViewById(R.id.tvExpression)

        observeViewModel()
        setupButtonListeners(view)
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            tvExpression.text = buildString {
                append(state.number1)
                state.operation?.let {
                    append(
                        when (it) {
                            is CalculatorOperation.Add -> "+"
                            is CalculatorOperation.Subtract -> "-"
                            is CalculatorOperation.Multiply -> "x"
                            is CalculatorOperation.Divide -> "/"
                        }
                    )
                }
                append(state.number2)
            }
        }
    }

    private fun setupButtonListeners(view: View) {
        val numbers = listOf(
            R.id.btn0 to 0, R.id.btn1 to 1, R.id.btn2 to 2, R.id.btn3 to 3,
            R.id.btn4 to 4, R.id.btn5 to 5, R.id.btn6 to 6, R.id.btn7 to 7,
            R.id.btn8 to 8, R.id.btn9 to 9
        )
        numbers.forEach { (id, number) ->
            view.findViewById<Button>(id).setOnClickListener {
                viewModel.onAction(CalculatorAction.Number(number))
            }
        }

        view.findViewById<Button>(R.id.btnPlus).setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        }
        view.findViewById<Button>(R.id.btnMinus).setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
        }
        view.findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        }
        view.findViewById<Button>(R.id.btnDivide).setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        }

        view.findViewById<Button>(R.id.btnDot).setOnClickListener {
            viewModel.onAction(CalculatorAction.Decimal)
        }
        view.findViewById<Button>(R.id.btnEquals).setOnClickListener {
            viewModel.onAction(CalculatorAction.Calculate)
        }
        view.findViewById<Button>(R.id.btnAC).setOnClickListener {
            viewModel.onAction(CalculatorAction.Clear)
        }
        view.findViewById<Button>(R.id.btnDel).setOnClickListener {
            viewModel.onAction(CalculatorAction.Delete)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}