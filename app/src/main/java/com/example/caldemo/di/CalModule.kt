package com.example.caldemo.di

import com.example.caldemo.domain.Calculator
import com.example.caldemo.fragment.calculator.factory.CalculatorFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CalModule {
    @Binds
    @Singleton
    abstract fun bindCalculator(
        calculatorFactory: CalculatorFactory
    ): Calculator
}
