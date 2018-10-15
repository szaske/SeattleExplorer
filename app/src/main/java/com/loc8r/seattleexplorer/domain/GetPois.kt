package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.SingleUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Single
import javax.inject.Inject

open class GetPois @Inject constructor(
        private val domainRepository: DomainRepository,
        observingThread: ObservingThread): SingleUseCaseBase<List<PoiDomain>, Nothing?>(observingThread) {

    override fun buildUseCaseSingle(params: Nothing?): Single<List<PoiDomain>> {
        return domainRepository.getPois()
    }

}