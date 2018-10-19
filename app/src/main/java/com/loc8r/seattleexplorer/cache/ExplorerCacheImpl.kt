package com.loc8r.seattleexplorer.cache

import com.loc8r.seattleexplorer.cache.models.CacheStatus
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheMapper
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerCache
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ExplorerCacheImpl @Inject constructor(
        private val explorerDatabase: ExplorerDatabase,
        private val mapper: PoiCacheMapper
) : ExplorerCache {

    companion object {
        const val CACHE_LENGTH_IN_DAYS = 1L
    }

    // TODO Currently Disabled
    override fun savePois(pois: List<PoiRepository>): Completable {
        return Completable.defer{
            explorerDatabase.poiCacheDao().savePois(
                    pois.map{
                        mapper.mapToCache(it) })
            Completable.complete()
        }
    }

    override fun getPois(): Maybe<List<PoiRepository>> {
        return explorerDatabase.poiCacheDao().getPois()
                .map {
                    it.map { mapper.mapToRepo(it) }
                }
    }

    override fun arePoisCached(): Single<Boolean> {
        return explorerDatabase.poiCacheDao().arePoisCached()
    }

    fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            explorerDatabase.cacheStatusDao().setCacheStatus(CacheStatus(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isPoisCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()

        // datetime in MS in which the cache expires
        val expirationTime = TimeUnit.DAYS.toMillis(CACHE_LENGTH_IN_DAYS)

        return explorerDatabase.cacheStatusDao().getCacheStatus()
                .onErrorReturn { CacheStatus(lastCacheTime = 0) }
                .map {
                    currentTime - it.lastCacheTime > expirationTime
                }
    }
}