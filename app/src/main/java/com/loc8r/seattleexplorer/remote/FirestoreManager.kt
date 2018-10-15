/**
 * Producer = the THING source of values
 * Observable = A class that takes a subscription object and Observer and sets up the connection
 * Observer = the object that watches an Observable.  It has next, error and complete methods
 */

package com.loc8r.seattleexplorer.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.loc8r.seattleexplorer.remote.models.FireStorePoiResponse
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerRemote
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class FireStoreManager @Inject constructor(
        private val database: FirebaseFirestore
): ExplorerRemote {

    companion object {
        private const val KEY_POIS = "pois"
        private const val POIS_ORDER = "name"
    }

    override fun getPois(): Single<List<PoiRepository>> {

        return Single.create {
            emitter: SingleEmitter<List<PoiRepository>> ->
            database.collection(KEY_POIS).orderBy(POIS_ORDER).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result!!.isEmpty) {
                            emitter.onError(IllegalStateException("Empty Poi List returned, Singles cannot be empty"))
                        } else {
                            emitter.onSuccess(task.result!!.toObjects(FireStorePoiResponse::class.java)
                                    .map { it -> it.mapToRepository() })
                        }
                    } else {
                        // An error occurred lets pass it to the Observable
                        emitter.onError(task.exception ?: UnknownError())
                    }
                }
        }

    }
}