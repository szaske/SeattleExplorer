package com.loc8r.seattleexplorer.domain.interfaces

import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DomainRepository {

    fun getPois(): Observable<List<PoiDomain>>

    fun getCollections(): Observable<List<CollectionDomain>>

    fun refreshAll(): Completable
}