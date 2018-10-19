package com.loc8r.seattleexplorer.di.modules

import android.app.Application
import com.loc8r.seattleexplorer.cache.ExplorerCacheImpl
import com.loc8r.seattleexplorer.cache.ExplorerDatabase
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideDataBase(application: Application): ExplorerDatabase {
            return ExplorerDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindExplorerCache(explorerCache: ExplorerCacheImpl): ExplorerCache
}