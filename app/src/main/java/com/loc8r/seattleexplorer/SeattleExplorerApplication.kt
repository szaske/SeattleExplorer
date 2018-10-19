package com.loc8r.seattleexplorer

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.google.firebase.FirebaseApp
import com.loc8r.seattleexplorer.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class SeattleExplorerApplication: Application(), HasActivityInjector, HasSupportFragmentInjector{

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    /** Returns an [AndroidInjector] of [Activity]s.  */
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    /** Returns an [AndroidInjector] of [Fragment]s.  */
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        // application() binds these components to the Application and return our builder
        // This class will appear unresolved until you build your project with all the
        // proper Dagger code.
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        FirebaseApp.initializeApp(this)
    }

}