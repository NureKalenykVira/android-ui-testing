package com.example.calculatorapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class MainActivityLoggerTest {

    @After
    fun tearDown() {
        MainActivity.testCalculator = null
    }

    private fun launchWithMockLogger(): ILogger {
        val logger = mock(ILogger::class.java)
        val calculator = Calculator(logger)
        MainActivity.testCalculator = calculator
        ActivityScenario.launch(MainActivity::class.java)
        return logger
    }

    private fun enterNumbers(a: String, b: String) {
        onView(withId(R.id.inputA)).perform(clearText(), typeText(a), closeSoftKeyboard())
        onView(withId(R.id.inputB)).perform(clearText(), typeText(b), closeSoftKeyboard())
    }

    @Test
    fun addOperation_LogsOnce_AndShowsCorrectResult() {
        val logger = launchWithMockLogger()

        enterNumbers("2", "3")
        onView(withId(R.id.btnAdd)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("5")))
        verify(logger, times(1)).log("Add: 2 + 3 = 5")
    }

    @Test
    fun addOperation_SecondCase_LogsOnce_AndShowsCorrectResult() {
        val logger = launchWithMockLogger()

        enterNumbers("10", "15")
        onView(withId(R.id.btnAdd)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("25")))
        verify(logger, times(1)).log("Add: 10 + 15 = 25")
    }

    @Test
    fun subtractOperation_LogsOnce_AndShowsCorrectResult() {
        val logger = launchWithMockLogger()

        enterNumbers("8", "3")
        onView(withId(R.id.btnSubtract)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("5")))
        verify(logger, times(1)).log("Subtract: 8 - 3 = 5")
    }

    @Test
    fun multiplyOperation_LogsOnce_AndShowsCorrectResult() {
        val logger = launchWithMockLogger()

        enterNumbers("4", "3")
        onView(withId(R.id.btnMultiply)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("12")))
        verify(logger, times(1)).log("Multiply: 4 * 3 = 12")
    }

    @Test
    fun divideOperation_LogsOnce_AndShowsCorrectResult() {
        val logger = launchWithMockLogger()

        enterNumbers("8", "2")
        onView(withId(R.id.btnDivide)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("4")))
        verify(logger, times(1)).log("Divide: 8 / 2 = 4")
    }

    @Test
    fun divideByZero_LogsOnce_AndShowsWarningMessage() {
        val logger = launchWithMockLogger()

        enterNumbers("8", "0")
        onView(withId(R.id.btnDivide)).perform(click())

        onView(withId(R.id.tvResult)).check(matches(withText("Cannot divide by zero")))
        verify(logger, times(1)).log("Divide: cannot divide 8 by zero")
    }
}