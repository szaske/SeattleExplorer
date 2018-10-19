package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.cache.ExplorerCacheImpl
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerCache
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class CacheDataStoreBroker @Inject constructor(
        private val cache: ExplorerCacheImpl
): ExplorerCache {

    override fun getPois(): Maybe<List<PoiRepository>> {
        return cache.getPois()
    }

    // When the local cache saves POIs to the POI table it always does 2 things
    override fun savePois(pois: List<PoiRepository>): Completable {
        return cache.savePois(pois)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                    .andThen(cache.setLastCacheTime(System.currentTimeMillis()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
    }

    override fun arePoisCached(): Single<Boolean> {
        return cache.arePoisCached()
    }

    override fun isPoisCacheExpired(): Single<Boolean> {
        return cache.isPoisCacheExpired()
    }
}