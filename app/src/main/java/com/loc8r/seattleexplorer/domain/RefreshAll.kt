package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.CompletableUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.DomainRepositoryInterface
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import io.reactivex.Completable
import javax.inject.Inject

open class RefreshAll @Inject constructor(
        private val domainRepository: DomainRepositoryInterface,
        observingThread: ObservingThreadInterface): CompletableUseCaseBase<Nothing?>(observingThread) {

    override fun buildUseCaseCompletable(params: Nothing?): Completable {
        return domainRepository.refreshAll()
    }
}