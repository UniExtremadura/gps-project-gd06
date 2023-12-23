package com.spotify.quavergd06


import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.spotify.quavergd06.view.home.HomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU04UITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = IntentsTestRule(HomeActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA"
        )

    @Test
    fun hU04UITest() {
        val imgCaptureResult = createImageCaptureActivityResultStub()
        Intents.intending(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.momentEditFragment), withContentDescription("Add Moment"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val constraintLayout = onView(
            allOf(
                withId(R.id.cameraOverlay),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val materialAutoCompleteTextView = onView(
            allOf(
                withId(R.id.detailSongTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialAutoCompleteTextView.perform(click())

        val materialAutoCompleteTextView2 = onView(
            allOf(
                withId(R.id.detailSongTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialAutoCompleteTextView2.perform(replaceText("Las 12"), closeSoftKeyboard())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.detailTitle), withText("Title"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Title test"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.detailTitle), withText("Title test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(closeSoftKeyboard())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.buttonSave),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())
        Thread.sleep(3000)
        val recyclerView = onView(
            allOf(
                withId(R.id.moment_show_grid),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(3000)
        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.buttonEdit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.detailTitle), withText("Title test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("Test title"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.detailTitle), withText("Test title"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.buttonSave),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.moment_show_grid),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withId(R.id.detailTitle), withText("Test title"),
                withParent(withParent(withId(R.id.cardView))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Test title")))
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
    private fun createImageCaptureActivityResultStub(): Instrumentation.ActivityResult {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val bundle = Bundle()
        bundle.putParcelable("data", BitmapFactory.decodeResource(context.resources, R.drawable.microsoft))
        // Create the Intent that will include the bundle.
        val resultData = Intent()
        resultData.putExtras(bundle)
        // Create the ActivityResult with the Intent.
        return Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
    }

}