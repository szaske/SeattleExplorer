package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.presentation.UiMainThread
import dagger.Binds
import dagger.Module

@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindObservingThread(observingThread: UiMainThread): ObservingThread
}