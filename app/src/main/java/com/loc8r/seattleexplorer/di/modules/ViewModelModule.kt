package com.loc8r.seattleexplorer.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.presentation.poi_list.PoiListViewModel
import com.loc8r.seattleexplorer.presentation.uiMainThread
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
internal abstract class ViewModelModule {

    @Binds
    abstract fun bindPostExecutionThread(uiMainThread: uiMainThread): ObservingThread

    @Binds
    @IntoMap
    @ViewModelKey(PoiListViewModel::class)
    abstract fun bindPoiDetailViewModel(viewModel: PoiListViewModel): ViewModel

    // Because I want to use my own View model factory instead of the default one, I need to tell
    // Dagger to inject the ViewModelFactory whenever a inject ViewModelFactory is requested
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

// Here I create my own custom annotation ViewModelKey
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)