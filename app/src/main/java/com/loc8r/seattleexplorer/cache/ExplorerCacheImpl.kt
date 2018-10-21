package com.loc8r.seattleexplorer.cache

import com.loc8r.seattleexplorer.cache.cacheStatus.LastCacheTime
import com.loc8r.seattleexplorer.cache.models.CacheStatus
import com.loc8r.seattleexplorer.cache.poiCache.CacheMapper
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerCache
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ExplorerCacheImpl @Inject constructor(
        private val explorerDatabase: ExplorerDatabase,
        private val mapper: CacheMapper
) : ExplorerCache {

    companion object {
        const val CACHE_LENGTH_IN_DAYS = 1L
    }

    override fun savePois(pois: List<PoiRepository>): Completable {
        return Completable.defer{
            explorerDatabase.poiCacheDao().savePois(
                    pois.map{
                        mapper.mapPoiToCache(it) })
            Completable.complete()
        }
    }

    override fun saveCollections(collections: List<CollectionRepository>): Completable {
        return Completable.defer{
            explorerDatabase.collectioncacheDao().saveCollections(
                    collections.map{
                        mapper.mapColToCache(it) })
            Completable.complete()
        }
    }

    override fun getPois(): Maybe<List<PoiRepository>> {
        return explorerDatabase.poiCacheDao().getPois()
                .map {
                    it.map { mapper.mapPoiToRepo(it) }
                }
    }

    override fun getCollections(): Maybe<List<CollectionRepository>> {
        return explorerDatabase.collectioncacheDao().getCollections()
                .map {
                    it.map { mapper.mapColToRepo(it) }
                }
    }

    override fun arePoisCached(): Single<Boolean> {
        return explorerDatabase.poiCacheDao().arePoisCached()
    }

    override fun areCollectionsCached(): Single<Boolean> {
        return explorerDatabase.collectioncacheDao().areCollectionsCached()
    }

    fun setLastPoisCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            explorerDatabase
                    .cacheStatusDao()
                    .setPoisCacheStatus(CacheStatus(id = LastCacheTime.POIS, lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    fun setLastColsCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            explorerDatabase
                    .cacheStatusDao()
                    .setPoisCacheStatus(CacheStatus(id = LastCacheTime.COLLECTION, lastCacheTime = lastCache))
            Completable.complete()
        }
    }



    override fun isPoisCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()

        // datetime in MS in which the cache expires
        val expirationTime = TimeUnit.DAYS.toMillis(CACHE_LENGTH_IN_DAYS)

        return explorerDatabase.cacheStatusDao().getPoisCacheStatus()
                .onErrorReturn { CacheStatus(id = LastCacheTime.POIS, lastCacheTime = 0) }
                .map {
                    currentTime - it.lastCacheTime > expirationTime
                }
    }

    override fun isCollectionsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()

        // datetime in MS in which the cache expires
        val expirationTime = TimeUnit.DAYS.toMillis(CACHE_LENGTH_IN_DAYS)

        return explorerDatabase.cacheStatusDao().getCollectionCacheStatus()
                .onErrorReturn { CacheStatus( id = LastCacheTime.COLLECTION, lastCacheTime = 0) }
                .map {
                    currentTime - it.lastCacheTime > expirationTime
                }
    }
}