package com.remyrobotics.graphql.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.helper.ApolloHttp
import com.remyrobotics.graphql.model.Location
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4ClassRunner::class)
class LocationsActivityTest {

    @get: Rule
    val activityRule: ActivityScenarioRule<LocationsActivity> = ActivityScenarioRule(LocationsActivity::class.java)

    private val latch: CountDownLatch = CountDownLatch(1)

    @Test
    fun isLocationListVisible() {
        ApolloHttp.queryLocations(1, "") { res, err ->
            latch.countDown()
        }
        latch.await()

        onView(ViewMatchers.withId(R.id.locationRecyclerView)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        onView(withId(R.id.locationRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(ViewMatchers.withId(R.id.residentRecyclerView)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}