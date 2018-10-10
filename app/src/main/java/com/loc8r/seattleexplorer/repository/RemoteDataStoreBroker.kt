package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.repository.interfaces.RepoDataStoreBroker
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import com.loc8r.seattleexplorer.utils.TestDataFactory
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class RemoteDataStoreBroker @Inject constructor(): RepoDataStoreBroker {
    override fun savePois(pois: List<PoiRepository>): Completable {
        // add functionality this is currently a placeholder
        return Completable.complete()
    }

    override fun getPois(): Observable<List<PoiRepository>> {

        return Observable.just(TestDataFactory.makePoiRepoList(4))
    }
}