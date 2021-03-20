package com.duyha.hilttestingsample.calulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.duyha.hilttestingsample.Calculator


interface CalculatorViewModelFactory : ViewModelProvider.Factory

@Suppress("UNCHECKED_CAST")
class CalculatorViewModelFactoryImpl(
    private val calculator: Calculator,
) : CalculatorViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            if (isAssignableFrom(CalculatorViewModel::class.java))
                CalculatorViewModel(calculator)
            else
                throw IllegalArgumentException("unknow $modelClass")
        } as T
}
