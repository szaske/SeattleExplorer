package com.loc8r.seattleexplorer.presentation.login

import android.Manifest
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.jraska.falcon.FalconSpoonRule
import com.loc8r.seattleexplorer.MainActivity
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.TestUtils.Companion.BLANK_LOGIN_INVALID_ERROR_TEXT
import com.loc8r.seattleexplorer.TestUtils.Companion.LOGIN_BAD_EMAIL_ERROR_TEXT
import com.loc8r.seattleexplorer.TestUtils.Companion.LOGIN_WRONG_PASSWORD_ERROR_TEXT
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    // Tells the test what activity to run
    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val falconSpoonRule = FalconSpoonRule()

    // This gets the name of the test
    @get:Rule
    var name = TestName()

    // Required to take snapshots
    @get:Rule
    var mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    private lateinit var activity: MainActivity

    @Before
    fun init() {
        activity = mActivityTestRule.activity
        // registers the LoginIdlingResource class, which is built into the Main activity and helps
        // Espresso understand when an asynchronous task is underway.  This tells the Espresso
        // library to wait until the event finishes before continuing with the instrumented test
        // instructions
        IdlingRegistry.getInstance().register(LoginIdlingResource(activity))
    }

    @Test
    fun loginMissingEmailAndPasswordGivesError() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            clickLogin()
            checkSnackBarDisplayedByMessageString(BLANK_LOGIN_INVALID_ERROR_TEXT)
            openMenu()
            checkMenuItemDoesntExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun loginBadEmailNoPasswordGivesError() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            setEmail("abc")
            clickLogin()
            checkSnackBarDisplayedByMessageString(BLANK_LOGIN_INVALID_ERROR_TEXT)
            openMenu()
            checkMenuItemDoesntExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun loginBadEmailShortPasswordGivesError() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            setEmail("abc")
            setPassword("abc")
            clickLogin()
            checkSnackBarDisplayedByMessageString(LOGIN_BAD_EMAIL_ERROR_TEXT)
            openMenu()
            checkMenuItemDoesntExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            falconSpoonRule.screenshot(mActivityTestRule.activity,name.methodName)
        }
    }

    @Test
    fun loginWrongPasswordGivesError() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            setEmail("test@test.com")
            setPassword("abc")
            clickLogin()
            checkSnackBarDisplayedByMessageString(LOGIN_WRONG_PASSWORD_ERROR_TEXT)
            openMenu()
            checkMenuItemDoesntExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            falconSpoonRule.screenshot(mActivityTestRule.activity,name.methodName)
        }
    }

    @Test
    fun loginEmailAndPasswordLoginsCorrectly() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            setEmail("test@test.com")
            setPassword("123456")
            clickLogin()
            val message = String.format(mActivityTestRule.activity.getString(R.string.welcome_returning_message), "Test Account")
            checkSnackBarDisplayedByMessageString(message)
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.home_fragment_title))
                    .check(matches(isDisplayed()))
            openMenu()
            checkMenuItemExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            falconSpoonRule.screenshot(mActivityTestRule.activity,name.methodName)
        }
    }

    @Test
    fun logoutExitsCorrectly() {
        login {
            // confirm we're on Login fragment
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            pressBack()
            setEmail("test@test.com")
            setPassword("123456")
            clickLogin()
            val message = String.format(mActivityTestRule.activity.getString(R.string.welcome_returning_message), "Test Account")
            checkSnackBarDisplayedByMessageString(message)
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.home_fragment_title))
                    .check(matches(isDisplayed()))
            openMenu()
            checkMenuItemExist(mActivityTestRule.activity
                    .getString(R.string.logout_menu_title))
            pressBack() // removes option menu
            logOut()
            matchToolbarTitle(mActivityTestRule.activity.getString(R.string.login_fragment_title))
                    .check(matches(isDisplayed()))
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

}