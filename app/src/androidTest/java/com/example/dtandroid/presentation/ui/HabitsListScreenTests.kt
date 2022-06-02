package com.example.dtandroid.presentation.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.entities.Type
import com.example.dtandroid.R
import com.example.dtandroid.TestUtils
import junit.framework.TestCase
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
internal class HabitsListScreenTests : TestCase() {

    private lateinit var scenario: FragmentScenario<ViewPagerFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_DTAndroid)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testAllViewsIsDisplayed() {
        onView(withId(R.id.habitTypesViewPager)).check(matches(isDisplayed()))
        onView(withId(R.id.habitsPagerTabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.floatingAddButton)).check(matches(isDisplayed()))
        onView(withId(R.id.habitsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testContentOfRecyclerViewsIsCorrect() {
        onView(allOf(withId(R.id.habitsRecyclerView), isDisplayed())).check(
            matches(
                TestUtils.atPosition(
                    0,
                    hasDescendant(
                        (withText(TestUtils.data.first { habitWithDoneDates -> habitWithDoneDates.habit.type == Type.Good }.habit.title))
                    )
                )
            )
        )
        onView(withId(R.id.habitTypesViewPager)).perform(ViewActions.swipeLeft())
        onView(allOf(withId(R.id.habitsRecyclerView), isDisplayed())).check(
            matches(
                TestUtils.atPosition(
                    0,
                    hasDescendant(withText(TestUtils.data.first { habitWithDoneDates -> habitWithDoneDates.habit.type == Type.Bad }.habit.title))
                )
            )
        )
    }

//    @Test
//    fun testOnButtonClick() {
//        onView(withId(R.id.floatingAddButton)).check(matches(isEnabled()))
//        onView(withId(R.id.floatingAddButton)).check(matches(isClickable()))
//        onView(withId(R.id.floatingAddButton)).perform(click())
//        onView(withId(R.id.habitCreationLayout)).check(matches(isDisplayed()))
//        onView(withId(R.id.floatingAddButton)).check(matches(not(isDisplayed())))
//    }

//    @Test
//    fun testOnDoneButtonClick(){
//        onView(withId(R.id.doneButton)).perform(click())
//        onView(withText(R.string.goodHabitDoneMessage)).check(matches(isDisplayed()))
//        onView(withId(R.id.doneButton)).perform(click())
//        onView(withText(R.string.goodHabitDoneMoreMessage)).check(matches(isDisplayed()))
//        onView(withId(R.id.habitTypesViewPager)).perform(ViewActions.swipeLeft())
//        onView(withId(R.id.doneButton)).perform(click())
//        onView(withText(R.string.badHabitDoneMessage)).check(matches(isDisplayed()))
//        onView(withId(R.id.doneButton)).perform(click())
//        onView(withText(R.string.badHabitDoneMoreMessage)).check(matches(isDisplayed()))
//    }
}