package com.example.calculatorapp

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityHistoryTest {

    private val prefsName = "calculator_prefs"

    @Before
    fun setUp() {
        clearPreferences()
    }

    @After
    fun tearDown() {
        clearPreferences()
    }

    private fun clearPreferences() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
    }

    private fun enterNumbers(a: String, b: String) {
        onView(withId(R.id.inputA)).perform(clearText(), typeText(a), closeSoftKeyboard())
        onView(withId(R.id.inputB)).perform(clearText(), typeText(b), closeSoftKeyboard())
    }

    @Test
    fun addOperation_SavesHistory_AndDisplaysItInUi() {
        ActivityScenario.launch(MainActivity::class.java)

        enterNumbers("2", "3")
        onView(withId(R.id.btnAdd)).perform(click())

        onView(withId(R.id.tvHistory)).check(matches(withText("2 + 3 = 5")))
    }

    @Test
    fun history_IsSaved_AfterActivityRecreation() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            enterNumbers("4", "3")
            onView(withId(R.id.btnMultiply)).perform(click())

            scenario.recreate()

            onView(withId(R.id.tvHistory)).check(matches(withText("4 * 3 = 12")))
        }
    }

    @Test
    fun divideByZero_IsSavedInHistory_AfterActivityRecreation() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            enterNumbers("8", "0")
            onView(withId(R.id.btnDivide)).perform(click())

            scenario.recreate()

            onView(withId(R.id.tvHistory))
                .check(matches(withText("8 / 0 = Cannot divide by zero")))
        }
    }

    @Test
    fun history_ShowsMultipleOperations() {
        ActivityScenario.launch(MainActivity::class.java)

        enterNumbers("2", "3")
        onView(withId(R.id.btnAdd)).perform(click())

        enterNumbers("10", "4")
        onView(withId(R.id.btnSubtract)).perform(click())

        onView(withId(R.id.tvHistory))
            .check(matches(withText("2 + 3 = 5\n10 - 4 = 6")))
    }

    @Test
    fun history_IsEmpty_AfterPreferencesCleared() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.tvHistory)).check(matches(withText("No history yet")))
    }
}