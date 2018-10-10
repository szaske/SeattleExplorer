package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.repository.DataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsDataRepository(dataRepository: DataRepository): DomainRepository
}