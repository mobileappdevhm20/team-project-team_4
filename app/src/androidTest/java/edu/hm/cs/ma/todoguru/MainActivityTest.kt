package edu.hm.cs.ma.todoguru

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val viewGroup = onView(
            allOf(
                withId(R.id.tasks_list_container),
                childAtPosition(
                    allOf(
                        withId(R.id.nav_host_fragment_container),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val viewGroup2 = onView(
            allOf(
                withId(R.id.topAppBar),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withText("ToDo Guru"),
                childAtPosition(
                    allOf(
                        withId(R.id.topAppBar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("ToDo Guru")))

        val linearLayoutCompat = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.topAppBar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayoutCompat.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.delete_tasks), withContentDescription("Delete"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.mark_tasks_as_done), withContentDescription("Done"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.tasks_list),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tasks_list_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val imageButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    allOf(
                        withId(R.id.tasks_list_container),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>,
        position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) &&
                    view == parent.getChildAt(position)
            }
        }
    }
}
