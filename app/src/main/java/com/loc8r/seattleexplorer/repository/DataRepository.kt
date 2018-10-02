package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.interfaces.ExplorerRepository
import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import com.loc8r.seattleexplorer.utils.TestDataFactory
import io.reactivex.Observable
import javax.inject.Inject

class DataRepository @Inject constructor(): ExplorerRepository {

    override fun getPois(): Observable<List<Poi_Domain>> {
        return Observable.just(TestDataFactory.makePOIList(4))
    }
}