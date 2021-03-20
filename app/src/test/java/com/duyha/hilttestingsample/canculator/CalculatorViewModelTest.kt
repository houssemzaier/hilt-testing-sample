package com.duyha.hilttestingsample.canculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.duyha.hilttestingsample.R
import com.duyha.hilttestingsample.calulator.CalculatorViewModel
import com.duyha.hilttestingsample.domain.Calculator
import com.duyha.hilttestingsample.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorViewModelTest {

    private lateinit var calculatorMock: Calculator
    private lateinit var sut: CalculatorViewModel

    private val textA = "4"
    private val a = textA.toInt()
    private val textB = "6"
    private val b = textB.toInt()
    private val sum = a + b

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        calculatorMock = mockk()
        sut = CalculatorViewModel(calculatorMock)
    }

    @Test
    fun test_SumReturnFromCalculator_LiveDataChanged() {
        //Given
        every { calculatorMock.sum(a, b) } returns sum
        //When
        sut.onSumClick(textA, textB)
        //Then
        verify { calculatorMock.sum(a, b) }
        assertEquals(sum, sut.sum.getOrAwaitValue())
    }

    @Test
    fun test_EmptyTextA_ShowMessage() {
        //Given
        every { calculatorMock.sum(a, b) } returns sum
        //When
        sut.onSumClick("", textB)
        //Then
        assertEquals(0, sut.sum.getOrAwaitValue())
        assertEquals(R.string.msg_input_a, sut.msg.getOrAwaitValue().peekContent())
    }

    @Test
    fun test_EmptyTextB_ShowMessage() {
        //Given
        every { calculatorMock.sum(a, b) } returns sum
        //When
        sut.onSumClick(textA, "")
        //Then
        assertEquals(0, sut.sum.getOrAwaitValue())
        assertEquals(R.string.msg_input_b, sut.msg.getOrAwaitValue().peekContent())
    }
}
