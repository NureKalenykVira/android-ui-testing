package com.example.calculatorapp

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class CalculatorUnitTest {

    private val logger = mock(ILogger::class.java)
    private val calculator = Calculator(logger)

    @Test
    fun add_TwoNumbers_ReturnsSum() {
        val result = calculator.add(2, 3)
        assertEquals(5, result)
    }

    @Test
    fun subtract_TwoNumbers_ReturnsDifference() {
        val result = calculator.subtract(5, 2)
        assertEquals(3, result)
    }
}