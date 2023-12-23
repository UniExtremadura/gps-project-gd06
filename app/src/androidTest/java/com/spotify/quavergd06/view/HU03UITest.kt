package com.spotify.quavergd06.view


import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.spotify.quavergd06.R
import com.spotify.quavergd06.view.home.HomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HU03UITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = IntentsTestRule(HomeActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA"
        )

    @Test
    fun hU03UITest() {
        val imgCaptureResult = createImageCaptureActivityResultStub()
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult)
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
        materialAutoCompleteTextView.perform(replaceText("Las 12"), closeSoftKeyboard())


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

        val textView = onView(
            allOf(
                withId(R.id.moment_title), withText("Title test"),
                withParent(withParent(withId(R.id.fragment_item))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Title test")))
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