package com.loc8r.seattleexplorer

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher


open class BaseTestRobot {

    fun fillEditText(resId: Int, text: String): ViewInteraction =
            onView(withId(resId)).perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

    fun clickButton(resId: Int): ViewInteraction = onView((withId(resId))).perform(ViewActions.click())

    fun openMenu(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
    }

    fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    fun selectMenuItem(menuString: String){
        onView(withText(menuString))
                .perform(click())
    }

    fun checkMenuItemDoesntExist(menuString: String){
        onView(withText(menuString)).check(ViewAssertions.doesNotExist())
    }

    fun checkMenuItemExist(menuString: String){
        onView(withText(menuString)).check(matches(isDisplayed()))
    }


    fun textView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        onData(anything())
                .inAdapterView(allOf(withId(listRes)))
                .atPosition(position).perform(ViewActions.click())
    }

    fun checkSnackBarDisplayedByMessageId(@StringRes message: Int) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(message)))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )))
    }

    fun checkSnackBarDisplayedByMessageString(message: String) {
        onView(withText(message))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )))
    }

    fun matchToolbarTitle(
            title: CharSequence): ViewInteraction {
        return onView(isAssignableFrom(Toolbar::class.java))
                .check(matches(withToolbarTitle(`is`(title))))
    }

    fun withToolbarTitle(
            textMatcher: Matcher<CharSequence>): Matcher<Any> {
        return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java!!) {
            public override fun matchesSafely(toolbar: Toolbar): Boolean {
                return textMatcher.matches(toolbar.title)
            }

            override fun describeTo(description: Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }
        }
    }

    fun pressHomeUp(){
        onView(
                Matchers.allOf(ViewMatchers.withContentDescription("Navigate up"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.main_tbar_toolbar),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.main_ll_container),
                                                0)),
                                2),
                        ViewMatchers.isDisplayed())).perform(ViewActions.click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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