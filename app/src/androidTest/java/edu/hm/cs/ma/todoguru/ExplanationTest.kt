package edu.hm.cs.ma.todoguru

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExplanationTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun explanationTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.button_next1), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_prev2), withText("Prev."),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.button_next1), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.button_next2), withText("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.button_skip3), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
    }
}
