package com.example.tddmasterclass

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.tddmasterclass.utils.TestIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
    private lateinit var signInIdlingResource: IdlingResource

    @Before
    open fun registerIdlingResource() {
        signInIdlingResource = TestIdlingResource.countingIdlingResource
        IdlingRegistry.getInstance().register(signInIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(signInIdlingResource)
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}