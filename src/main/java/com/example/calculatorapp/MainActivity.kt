package com.example.calculatorapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        var testCalculator: Calculator? = null
    }

    private lateinit var calculator: Calculator
    private lateinit var tvHistory: TextView

    private val prefsName = "calculator_prefs"
    private val historyKey = "history"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculator = testCalculator ?: Calculator(AppLogger())

        val inputA = findViewById<EditText>(R.id.inputA)
        val inputB = findViewById<EditText>(R.id.inputB)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        tvHistory = findViewById(R.id.tvHistory)

        loadHistory()

        btnAdd.setOnClickListener {
            val a = inputA.text.toString().toIntOrNull() ?: 0
            val b = inputB.text.toString().toIntOrNull() ?: 0
            val result = calculator.add(a, b)
            tvResult.text = result.toString()
            saveHistoryEntry("$a + $b = $result")
        }

        btnSubtract.setOnClickListener {
            val a = inputA.text.toString().toIntOrNull() ?: 0
            val b = inputB.text.toString().toIntOrNull() ?: 0
            val result = calculator.subtract(a, b)
            tvResult.text = result.toString()
            saveHistoryEntry("$a - $b = $result")
        }

        btnMultiply.setOnClickListener {
            val a = inputA.text.toString().toIntOrNull() ?: 0
            val b = inputB.text.toString().toIntOrNull() ?: 0
            val result = calculator.multiply(a, b)
            tvResult.text = result.toString()
            saveHistoryEntry("$a * $b = $result")
        }

        btnDivide.setOnClickListener {
            val a = inputA.text.toString().toIntOrNull() ?: 0
            val b = inputB.text.toString().toIntOrNull() ?: 0
            val result = calculator.divide(a, b)

            if (result == null) {
                tvResult.text = "Cannot divide by zero"
                saveHistoryEntry("$a / $b = Cannot divide by zero")
            } else {
                tvResult.text = result.toString()
                saveHistoryEntry("$a / $b = $result")
            }
        }
    }

    private fun saveHistoryEntry(entry: String) {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val currentHistory = prefs.getString(historyKey, "") ?: ""
        val newHistory = if (currentHistory.isBlank()) entry else "$currentHistory\n$entry"

        prefs.edit().putString(historyKey, newHistory).apply()
        tvHistory.text = newHistory
    }

    private fun loadHistory() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val history = prefs.getString(historyKey, "") ?: ""
        tvHistory.text = if (history.isBlank()) "No history yet" else history
    }
}