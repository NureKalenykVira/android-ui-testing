package com.example.calculatorapp

class Calculator(private val logger: ILogger) {

    fun add(a: Int, b: Int): Int {
        val result = a + b
        logger.log("Add: $a + $b = $result")
        return result
    }

    fun subtract(a: Int, b: Int): Int {
        val result = a - b
        logger.log("Subtract: $a - $b = $result")
        return result
    }

    fun multiply(a: Int, b: Int): Int {
        val result = a * b
        logger.log("Multiply: $a * $b = $result")
        return result
    }

    fun divide(a: Int, b: Int): Int? {
        return if (b == 0) {
            logger.log("Divide: cannot divide $a by zero")
            null
        } else {
            val result = a / b
            logger.log("Divide: $a / $b = $result")
            result
        }
    }
}