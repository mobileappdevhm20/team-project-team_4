package edu.hm.cs.ma.todoguru.subtask

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.hm.cs.ma.todoguru.MainActivity
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import edu.hm.cs.ma.todoguru.skipToOnCreateTaskFragment
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertSubTaskActionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertSubTaskActionTest() {
        skipToOnCreateTaskFragment()

        val chip = onView(
            allOf(
                withId(R.id.chip_set_sub_task), withText("Set sub tasks"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.createTaskButton),
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
        floatingActionButton2.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.subTaskDescription),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.custom),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Subtask"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("Confirm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.display_task), withContentDescription("Display task"),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.sub_task_list),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("Confirm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.createTaskButton),
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
        floatingActionButton3.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.subTaskDescription),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.custom),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Sub-Task"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("Confirm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton4.perform(scrollTo(), click())
    }
}
