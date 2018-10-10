package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Observable
import javax.inject.Inject

open class GetPois @Inject constructor(
        private val domainRepository: DomainRepository,
        observingThread: ObservingThread): ObservableUseCaseBase<List<PoiDomain>, Nothing?>(observingThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<PoiDomain>> {
        return domainRepository.getPois()
    }

}