package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ExplorerCache {

    // This saves Poi to the cache from the remote database
    fun savePois(pois: List<PoiRepository>): Completable

    // This gets us the Observable list of Pois
    fun getPois(): Observable<List<PoiRepository>>

    fun arePoisCached(): Single<Boolean>

    fun isPoisCacheExpired(): Single<Boolean>
}