package com.loc8r.seattleexplorer.presentation.login

import android.Manifest
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.jraska.falcon.FalconSpoonRule
import com.loc8r.seattleexplorer.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val falconSpoonRule = FalconSpoonRule()

    // Required to take snapshots
    @get:Rule
    var mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    @Before
    fun init() {
    }


    @Test
    fun loginMissingEmailPassword() {
        login {
            matchToolbarTitle("Home")
                    .check(matches(isDisplayed()))
            //logOut()
            //clickLogin()
            falconSpoonRule.screenshot(mActivityTestRule.activity,"loginMissingEmailPassword")
            // screenShot(falconSpoonRule, "loginMissingEmailPassword")
            // checkSnackBarDisplayedByMessage(R.string.auth_error_text)
            // matchErrorText(string(R.string.missing_fields))
        }
    }

}