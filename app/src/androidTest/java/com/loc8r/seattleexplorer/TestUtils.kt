package com.loc8r.seattleexplorer

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import com.jraska.falcon.FalconSpoonRule
import timber.log.Timber


class TestUtils {

    companion object {

        private fun getActivityInstance(): Activity {
            val currentActivity = arrayOf<Activity>()

            val monitor: Runnable = Runnable {
                val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
                val it = resumedActivity.iterator()
                currentActivity[0] = it.next()
            }

            getInstrumentation().runOnMainSync(monitor)
            return currentActivity[0]
        }


        fun getCurrentActivity(): Activity {
            val currentActivity = arrayOf<Activity>()
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
                val it = resumedActivity.iterator()
                currentActivity[0] = it.next()
            }

            return currentActivity[0]
        }

        fun screenShot(rule: FalconSpoonRule, tag: String) {
            //rule.screenshot(getCurrentActivity(), tag)
            rule.screenshot(getActivityInstance(), tag)
            Timber.i("Screenshot taken: $tag")
        }
    }
}