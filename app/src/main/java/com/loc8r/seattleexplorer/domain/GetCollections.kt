package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.base.SingleUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

open class GetCollections @Inject constructor(
        private val domainRepository: DomainRepository,
        observingThread: ObservingThread): ObservableUseCaseBase<List<CollectionDomain>, Nothing?>(observingThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<CollectionDomain>> {
        return domainRepository.getCollections()
    }

}