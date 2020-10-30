package com.lukieoo.compassnetguru


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EsspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Test
    fun esspressoTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.btnCoordinates), withText("set your target"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.latitude), withText("0.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayout3),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("20.0"))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.latitude), withText("20.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayout3),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.latitude), withText("20.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayout3),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(pressImeActionButton())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.longitude), withText("0.0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayout4),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(pressImeActionButton())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.btnSetCoordinates), withText("set cordinates"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment_container),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.btnCoordinates), withText("set your target"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.btnSetCoordinates), withText("set cordinates"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment_container),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton4.perform(click())
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
