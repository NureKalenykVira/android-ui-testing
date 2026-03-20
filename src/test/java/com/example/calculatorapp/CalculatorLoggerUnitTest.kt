package com.example.calculatorapp

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class CalculatorLoggerUnitTest {

    private val logger = mock(ILogger::class.java)
    private val calculator = Calculator(logger)

    @Test
    fun add_LogsOnce_AndReturnsCorrectResult() {
        val result = calculator.add(2, 3)

        assertEquals(5, result)
        verify(logger, times(1)).log("Add: 2 + 3 = 5")
    }

    @Test
    fun subtract_LogsOnce_AndReturnsCorrectResult() {
        val result = calculator.subtract(8, 3)

        assertEquals(5, result)
        verify(logger, times(1)).log("Subtract: 8 - 3 = 5")
    }

    @Test
    fun multiply_LogsOnce_AndReturnsCorrectResult() {
        val result = calculator.multiply(4, 3)

        assertEquals(12, result)
        verify(logger, times(1)).log("Multiply: 4 * 3 = 12")
    }

    @Test
    fun divide_LogsOnce_AndReturnsCorrectResult() {
        val result = calculator.divide(8, 2)

        assertEquals(4, result)
        verify(logger, times(1)).log("Divide: 8 / 2 = 4")
    }

    @Test
    fun divideByZero_LogsOnce_AndReturnsNull() {
        val result = calculator.divide(8, 0)

        assertEquals(null, result)
        verify(logger, times(1)).log("Divide: cannot divide 8 by zero")
    }
}