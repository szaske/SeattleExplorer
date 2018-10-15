package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.repository.interfaces.RepoDataStoreBroker
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import com.loc8r.seattleexplorer.utils.TestDataFactory
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class CacheDataStoreBroker @Inject constructor(): RepoDataStoreBroker {

    override fun savePois(pois: List<PoiRepository>): Completable {
        return Completable.complete()
    }

    override fun getPois(): Single<List<PoiRepository>> {
        // temporary code to supply some fake data
        return Single.just(TestDataFactory.makePoiRepoList(4))
    }

    open fun arePoisCached(): Single<Boolean> {
        // temporary code to force the use of the cache
        return Single.just(false)
    }

    open fun isPoisCacheExpired(): Single<Boolean> {
        return Single.just(false)
    }

}