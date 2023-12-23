package com.spotify.quavergd06.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.spotify.quavergd06.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.pressBack

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU20UITest {

    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        var currentIndex = 0

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun hU20UITest() {
        val frameLayout = onView(
            allOf(
                withId(R.id.statsFragment), withContentDescription("Stats"),
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

        val recyclerView = onView(
            allOf(
                withIndex(withId(R.id.recycler_view_top_preview), 0),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()
        val materialButton = onView(
            allOf(
                withId(R.id.moreTopArtists), withText("More..."),
                childAtPosition(
                    withParent(withId(R.id.top_item_view_pager)),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val recyclerView2 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 0),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()
        val tabView = onView(
            allOf(
                withContentDescription("6 Months"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.top_item_tab_layout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val recyclerView3 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 1),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()
        val tabView2 = onView(
            allOf(
                withContentDescription("All Time"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.top_item_tab_layout),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView2.perform(click())

        val recyclerView4 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 1),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView4.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()
        pressBack()

        val recyclerView5 = onView(
            allOf(
                withIndex(withId(R.id.recycler_view_top_preview), 1),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView5.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val button = onView(
            allOf(
                withId(R.id.artist_name),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton2 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        // Back to personal
        pressBack()
        pressBack()
        val materialButton3 = onView(
            allOf(
                withId(R.id.moreTopTracks), withText("More..."),
                childAtPosition(
                    withParent(withId(R.id.top_item_view_pager)),
                    6
                ),
                isDisplayed()
            )
        )
        // Click more
        materialButton3.perform(click())

        // RecyclerView 4 weeks
        val recyclerView6 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 0),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView6.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton4 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        // Back to 4 weeks
        pressBack()
        pressBack()
        val tabView3 = onView(
            allOf(
                withContentDescription("6 Months"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.top_item_tab_layout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView3.perform(click())

        // RecyclerView 6 months
        val recyclerView7 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 1),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView7.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton5 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        // Back to 6 months
        pressBack()
        pressBack()
        val tabView4 = onView(
            allOf(
                withContentDescription("All Time"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.top_item_tab_layout),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView4.perform(click())

        // RecyclerView AllTime
        val recyclerView8 = onView(
            allOf(
                withIndex(withId(R.id.top_item_recycler_view), 1),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView8.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val button4 = onView(
            allOf(
                withId(R.id.artist_name),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button4.check(matches(isDisplayed()))

        val materialButton6 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        // Back to Personal
        pressBack()
        pressBack()
        pressBack()
        val recyclerView9 = onView(
            allOf(
                withIndex(withId(R.id.recycler_view_top_preview), 2),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView9.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val button5 = onView(
            allOf(
                withId(R.id.artist_name),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button5.check(matches(isDisplayed()))

        val materialButton7 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        pressBack()
        pressBack()
        val tabView5 = onView(
            allOf(
                withContentDescription("Global Top"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.personal_global_tab_layout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView5.perform(click())

        val recyclerView10 = onView(
            allOf(
                withId(R.id.top_item_recycler_view),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView10.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val button6 = onView(
            allOf(
                withId(R.id.artist_name),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button6.check(matches(isDisplayed()))

        val materialButton8 = onView(
            allOf(
                withId(R.id.artist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())

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