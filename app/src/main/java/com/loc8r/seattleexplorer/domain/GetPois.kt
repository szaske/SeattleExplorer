package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepositoryInterface
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Observable
import javax.inject.Inject

open class GetPois @Inject constructor(
        private val domainRepository: DomainRepositoryInterface,
        observingThread: ObservingThreadInterface): ObservableUseCaseBase<List<PoiDomain>, Nothing?>(observingThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<PoiDomain>> {
        return domainRepository.getPois()
    }

}