package com.spotify.quavergd06.view

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.spotify.quavergd06.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU02UITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun hU02UITest() {
        Thread.sleep(5000)
        val frameLayout = onView(
            allOf(
                withId(R.id.profileFragment), withContentDescription("Profile"),
                withParent(withParent(withId(R.id.bottom_navigation))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                        withParent(
                            allOf(
                                withId(R.id.profileFragment),
                                withContentDescription("Profile")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.profileFragment), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.unlog_button), withText("CLOSE SESSION"),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton = onView(
            allOf(
                withId(R.id.unlog_button), withText("Close session"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}