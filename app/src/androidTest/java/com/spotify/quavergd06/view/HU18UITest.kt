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
class HU18UITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun hU18UITest() {

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
                withId(R.id.titleTopArtists), withText("Top Artists:"),
                withParent(withParent(withId(R.id.top_item_view_pager))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Top Artists:")))

        val button = onView(
            allOf(
                withId(R.id.moreTopArtists), withText("MORE..."),
                withParent(withParent(withId(R.id.top_item_view_pager))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.recycler_view_top_preview),
                withParent(withParent(withId(R.id.fragmentTopArtists))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val materialButton2 = onView(
            allOf(
                withId(R.id.moreTopArtists), withText("More..."),
                childAtPosition(
                    withParent(withId(R.id.top_item_view_pager)),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val linearLayout = onView(
            allOf(
                withContentDescription("4 Weeks"),
                withParent(withParent(withId(R.id.top_item_tab_layout))),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withText("4 WEEKS"),
                withParent(
                    allOf(
                        withContentDescription("4 Weeks"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("4 WEEKS")))

        val textView3 = onView(
            allOf(
                withText("6 MONTHS"),
                withParent(
                    allOf(
                        withContentDescription("6 Months"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("6 MONTHS")))

        val linearLayout2 = onView(
            allOf(
                withContentDescription("6 Months"),
                withParent(withParent(withId(R.id.top_item_tab_layout))),
                isDisplayed()
            )
        )
        linearLayout2.check(matches(isDisplayed()))

        val textView4 = onView(
            allOf(
                withText("ALL TIME"),
                withParent(
                    allOf(
                        withContentDescription("All Time"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("ALL TIME")))

        val linearLayout3 = onView(
            allOf(
                withContentDescription("All Time"),
                withParent(withParent(withId(R.id.top_item_tab_layout))),
                isDisplayed()
            )
        )
        linearLayout3.check(matches(isDisplayed()))

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

        val tabView3 = onView(
            allOf(
                withContentDescription("4 Weeks"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.top_item_tab_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tabView3.perform(click())
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