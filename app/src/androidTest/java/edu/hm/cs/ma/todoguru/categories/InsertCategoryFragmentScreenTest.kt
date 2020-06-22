package edu.hm.cs.ma.todoguru.categories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.hm.cs.ma.todoguru.MainActivity
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertCategoryFragmentScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertCategoryFragmentScreenTest() {
        skipToSetCategoryDialog()

        val linearLayoutCompat = onView(
            allOf(
                withId(R.id.parentPanel),
                childAtPosition(
                    allOf(
                        withId(android.R.id.content),
                        childAtPosition(
                            withId(R.id.action_bar_root),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayoutCompat.check(matches(isDisplayed()))

        val linearLayout = onView(
            allOf(
                withId(R.id.topPanel),
                childAtPosition(
                    allOf(
                        withId(R.id.parentPanel),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val frameLayout = onView(
            allOf(
                withId(R.id.contentPanel),
                childAtPosition(
                    allOf(
                        withId(R.id.parentPanel),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))
    }
}
