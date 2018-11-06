package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepositoryInterface
import com.loc8r.seattleexplorer.repository.DataRepositoryInterface
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsDataRepository(dataRepository: DataRepositoryInterface): DomainRepositoryInterface
}