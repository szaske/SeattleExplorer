package com.loc8r.seattleexplorer.presentation.register

import android.Manifest
import android.support.test.espresso.IdlingRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.jraska.falcon.FalconSpoonRule
import com.loc8r.seattleexplorer.MainActivity
import com.loc8r.seattleexplorer.R.id.home_btn_goto_colList
import com.loc8r.seattleexplorer.TestUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterFragmentTest {

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

        // registers the RegisterIdlingResource class, which is built into the Main activity and helps
        // Espresso understand when an asynchronous task is underway.  This tells the Espresso
        // library to wait until the event finishes before continuing with the instrumented test
        // instructions
        IdlingRegistry.getInstance().register(RegisterIdlingResource(activity))
    }

    @Test
    fun registerAllBlankGivesError() {
        register {
            startInRegister(activity)
            clickRegister()
            checkSnackBarDisplayedByMessageString(TestUtils.REGISTER_INVALID_ERROR_MESSAGE)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun registerNameOnlyGivesError() {
        register {
            startInRegister(activity)
            setName("Steve")
            clickRegister()
            checkSnackBarDisplayedByMessageString(TestUtils.REGISTER_INVALID_ERROR_MESSAGE)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun registerEmailOnlyGivesError() {
        register {
            startInRegister(activity)
            setEmail("test@nothing.com")
            clickRegister()
            checkSnackBarDisplayedByMessageString(TestUtils.REGISTER_INVALID_ERROR_MESSAGE)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun registerPasswordOnlyGivesError() {
        register {
            startInRegister(activity)
            setPassword("123456")
            clickRegister()
            checkSnackBarDisplayedByMessageString(TestUtils.REGISTER_INVALID_ERROR_MESSAGE)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun registerExistingAccountGivesError() {
        register {
            startInRegister(activity)
            setName("Test Account")
            setEmail("test@test.com")
            setPassword("123456")
            clickRegister()
            checkSnackBarDisplayedByMessageString(TestUtils.REGISTER_INVALID_ERROR_MESSAGE)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun registerNewUserWorks() {
        register {
            startInRegister(activity)
            setName("Test Account")
            setEmail("test123@test123.com")
            setPassword("123456")
            clickRegister()
            deleteUser(activity)
            falconSpoonRule.screenshot(activity,name.methodName)
        }
    }

    @Test
    fun LongTest() {
        register {
            startInRegister(activity)
            setName("Test Account")
            setEmail("test123@test123.com")
            setPassword("123456")
            clickRegister()

            // falconSpoonRule.screenshot(activity,name.methodName)
            clickButton(home_btn_goto_colList)
            pressHomeUp()
            deleteUser(activity)
        }
    }



}