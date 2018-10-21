package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.repository.interfaces.ExplorerRemote
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class RemoteDataStoreBroker @Inject constructor(
        private val firestore: ExplorerRemote
): ExplorerRemote {

    override fun getPois(): Single<List<PoiRepository>> {
        return firestore.getPois()
    }

    override fun getCollections(): Single<List<CollectionRepository>> {
        return firestore.getCollections()
    }

    fun savePois(pois: List<PoiRepository>): Completable {
        throw UnsupportedOperationException("Function savePois is not supported by the remote data store")
    }

    fun arePoisCached(): Single<Boolean> {
        throw UnsupportedOperationException("Function arePoisCached is not supported by the remote data store")
    }

    fun isPoisCacheExpired(): Single<Boolean> {
        throw UnsupportedOperationException("Function isPoisCacheExpired is not supported by the remote data store")
    }

}