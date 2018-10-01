package com.loc8r.seattleexplorer.di

import android.app.Application
import com.loc8r.seattleexplorer.SeattleExplorerApplication
import com.loc8r.seattleexplorer.di.modules.ActivityModule
import com.loc8r.seattleexplorer.di.modules.FragmentModule
import com.loc8r.seattleexplorer.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class])
interface AppComponent {

    //I now need to provide a way for this component to be constructed
    @Component.Builder
    interface Builder {

        // BindsInstance gives me a way to bind the component to the application
        // meaning I can use the application itself to retrieve the component builder
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // This function will take our application and inject the dependencies I define
    fun inject(app: SeattleExplorerApplication)
}