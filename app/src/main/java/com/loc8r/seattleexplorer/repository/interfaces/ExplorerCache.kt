package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.*

interface ExplorerCache {

    // This saves Poi to the cache from the remote database
    fun savePois(pois: List<PoiRepository>): Completable

    fun saveCollections(collections: List<CollectionRepository>): Completable

    // This gets us the Single list of Pois
    fun getPois(): Maybe<List<PoiRepository>>

    fun getCollections(): Maybe<List<CollectionRepository>>

    fun arePoisCached(): Single<Boolean>

    fun areCollectionsCached(): Single<Boolean>

    fun isPoisCacheExpired(): Single<Boolean>

    fun isCollectionsCacheExpired(): Single<Boolean>
}