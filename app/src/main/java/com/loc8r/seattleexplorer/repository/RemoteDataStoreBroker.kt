package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.remote.FireStoreManager
import com.loc8r.seattleexplorer.repository.interfaces.RepoDataStoreBroker
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

open class RemoteDataStoreBroker @Inject constructor(
        private val firestore: FireStoreManager
): RepoDataStoreBroker {
    override fun savePois(pois: List<PoiRepository>): Completable {
        // add functionality this is currently a placeholder
        return Completable.complete()
    }

    override fun getPois(): Single<List<PoiRepository>> {
        return firestore.getPois()
    }
}