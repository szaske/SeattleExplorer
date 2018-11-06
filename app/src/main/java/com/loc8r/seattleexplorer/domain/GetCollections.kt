package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepositoryInterface
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import io.reactivex.Observable
import javax.inject.Inject

open class GetCollections @Inject constructor(
        private val domainRepository: DomainRepositoryInterface,
        observingThread: ObservingThreadInterface): ObservableUseCaseBase<List<CollectionDomain>, Nothing?>(observingThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<CollectionDomain>> {
        return domainRepository.getCollections()
    }

}