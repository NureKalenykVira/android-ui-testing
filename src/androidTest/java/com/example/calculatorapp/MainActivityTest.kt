package com.example.calculatorapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MainActivityTest(
    private val firstNumber: String,
    private val secondNumber: String,
    private val operationButtonId: Int,
    private val expectedResult: String
) {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0}, {1} -> {3}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("2", "3", R.id.btnAdd, "5"),
                arrayOf("10", "15", R.id.btnAdd, "25"),
                arrayOf("8", "3", R.id.btnSubtract, "5"),
                arrayOf("20", "7", R.id.btnSubtract, "13"),
                arrayOf("4", "3", R.id.btnMultiply, "12"),
                arrayOf("6", "5", R.id.btnMultiply, "30"),
                arrayOf("8", "2", R.id.btnDivide, "4"),
                arrayOf("18", "3", R.id.btnDivide, "6"),
                arrayOf("8", "0", R.id.btnDivide, "Cannot divide by zero")
            )
        }
    }

    private fun enterNumbers(a: String, b: String) {
        onView(withId(R.id.inputA)).perform(clearText(), typeText(a), closeSoftKeyboard())
        onView(withId(R.id.inputB)).perform(clearText(), typeText(b), closeSoftKeyboard())
    }

    @Test
    fun calculatorOperation_ShowsCorrectResult() {
        enterNumbers(firstNumber, secondNumber)
        onView(withId(operationButtonId)).perform(click())
        onView(withId(R.id.tvResult)).check(matches(withText(expectedResult)))
    }
}