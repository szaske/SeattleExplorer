package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}