package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.*

interface ExplorerCache {

    // This saves Poi to the cache from the remote database
    fun savePois(pois: List<PoiRepository>): Completable

    // This gets us the Single list of Pois
    fun getPois(): Maybe<List<PoiRepository>>

    fun arePoisCached(): Single<Boolean>

    fun isPoisCacheExpired(): Single<Boolean>

}