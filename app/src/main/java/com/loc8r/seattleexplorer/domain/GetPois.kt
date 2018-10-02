package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.base.ObservableUseCaseBase
import com.loc8r.seattleexplorer.domain.interfaces.ExplorerRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import io.reactivex.Observable
import javax.inject.Inject

open class GetPois @Inject constructor(
        private val explorerRepository: ExplorerRepository,
        observingThread: ObservingThread): ObservableUseCaseBase<List<Poi_Domain>, Nothing?>(observingThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Poi_Domain>> {
        return explorerRepository.getPois()
    }

}