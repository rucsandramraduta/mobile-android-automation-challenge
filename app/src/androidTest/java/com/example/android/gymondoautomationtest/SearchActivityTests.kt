package com.example.android.gymondoautomationtest

import androidx.core.view.children
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.layout_item.view.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

typealias VH = ListActivity.MyAdapter.MyViewHolder

class SearchActivityTests {

    @get:Rule
    var activityRule: ActivityTestRule<ListActivity> = ActivityTestRule(ListActivity::class.java)


    @Test
    // Perform search using the workout number
    fun searchForWorkoutUsingWorkoutNo() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("436"))
        onView(withId(R.id.btnSearch)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("436 - 10 Min Abs"))))

    }


    @Test
    // Perform search using a part of the workout name
    fun searchForWorkoutUsingPartialName() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("10 Min Abs"))
        onView(withId(R.id.btnSearch)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("436 - 10 Min Abs"))))
    }


    @Test
    // Perform search using an inner string
    fun searchForWorkoutUsingInnerString() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("wes"))
        onView(withId(R.id.btnSearch)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("573 - Awesome"))))
    }

    @Test
    // Perform search of a non existing workout name
    fun searchForNonExistingWorkout() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("jibberish"))
        onView(withId(R.id.btnSearch)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasChildCount(0)))
    }


    /*@Test
    fun searchForWorkoutWithSpecialCharacters() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("ä"))
        onView(withId(R.id.btnSearch)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("434 - Armhävning"))))
    }*/

    @Test
    // Test the functionality of the clear button
    fun clearSearchField() {
        onView(withId(R.id.editTxtSearch)).perform(typeText("workout"))
        onView(withId(R.id.btnClear)).perform(click())
        onView(withId(R.id.editTxtSearch)).check(matches(withText("")))

    }

    @Test
    // Alternative method to search for a workout
    fun searchForWorkout() {

        onView(withId(R.id.editTxtSearch)).perform(typeText("436"))
        onView(withId(R.id.btnSearch)).perform(click())

        val firstElementView = activityRule.activity.recycler_view.children.toList()[0]
        assertEquals("436 - 10 Min Abs", firstElementView.item_text.text)
    }
}