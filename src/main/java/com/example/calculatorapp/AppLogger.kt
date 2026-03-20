package com.example.calculatorapp

import android.util.Log

class AppLogger : ILogger {
    override fun log(message: String) {
        Log.d("CalculatorApp", message)
    }
}