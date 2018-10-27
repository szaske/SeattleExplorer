package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.CompletableUseCaseBase
import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.base.SingleUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

open class RefreshAll @Inject constructor(
    private val domainRepository: DomainRepository,
    observingThread: ObservingThread): CompletableUseCaseBase<Nothing?>(observingThread) {

    override fun buildUseCaseCompletable(params: Nothing?): Completable {
        return domainRepository.refreshAll()
    }
}