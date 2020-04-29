package com.blkxltng.githubbrowserredux

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoRetrievalTest {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkOrg_found() {

        // Type text and then press the button.
        onView(withId(R.id.orgEditText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard())
        onView(withId(R.id.searchButton)).perform(click())

        // TODO find a better way to check when the response is retrieved
        Thread.sleep(3000)
        onView(withId(R.id.errorText)).check(matches(withText("Google")))

    }

    @Test
    fun check_noInput() {
        // Type text and then press the button.
        onView(withId(R.id.orgEditText)).perform(typeText(STRING_TO_BE_TYPED_FAILURE), closeSoftKeyboard())
        onView(withId(R.id.searchButton)).perform(click())

        onView(withId(R.id.errorText)).check(matches(withText("Please input a organization username.")))
    }

    @Test
    fun click_Repo() {

        // Type text and then press the button.
        onView(withId(R.id.orgEditText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard())
        onView(withId(R.id.searchButton)).perform(click())

        // TODO find a better way to check when the response is retrieved
        Thread.sleep(3000)
        onView(withId(R.id.orgName)).check(matches(withText("Google")))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

    }

    companion object {

        const val STRING_TO_BE_TYPED = "Google"
        const val STRING_TO_BE_TYPED_FAILURE = ""
    }
}