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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU22UITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun hU22UITest() {
        val imageView = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                        withParent(
                            allOf(
                                withId(R.id.statsFragment),
                                withContentDescription("Stats")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val frameLayout = onView(
            allOf(
                withId(R.id.statsFragment), withContentDescription("Stats"),
                withParent(withParent(withId(R.id.bottom_navigation))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.statsFragment), withContentDescription("Stats"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(
                withText("PERSONAL"),
                withParent(
                    allOf(
                        withContentDescription("Personal"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("PERSONAL")))

        val linearLayout = onView(
            allOf(
                withContentDescription("Personal"),
                withParent(withParent(withId(R.id.personal_global_tab_layout))),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.titleHistory), withText("History:"),
                withParent(withParent(withId(R.id.top_item_view_pager))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("History:")))

        val button = onView(
            allOf(
                withId(R.id.moreHistory), withText("MORE..."),
                withParent(withParent(withId(R.id.top_item_view_pager))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton = onView(
            allOf(
                withId(R.id.moreHistory), withText("More..."),
                childAtPosition(
                    withParent(withId(R.id.top_item_view_pager)),
                    9
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.top_item_recycler_view),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))
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