/**
 * Producer = the THING source of values
 * Observable = A class that takes a subscription object and Observer and sets up the connection
 * Observer = the object that watches an Observable.  It has next, error and complete methods
 */

package com.loc8r.seattleexplorer.remote

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.loc8r.seattleexplorer.remote.models.FireStorePoiResponse
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerRemote
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject




class FireStoreManager @Inject constructor(
        private val database: FirebaseFirestore
): ExplorerRemote {

    companion object {
        private const val KEY_POIS = "pois"
        private val TAG = FireStoreManager::class.qualifiedName
    }

    override fun getPois(): Observable<List<PoiRepository>> {

        return Observable.create { emitter: ObservableEmitter<List<PoiRepository>> ->

            // Start by creating the Observable producer, that being Firestore event Listener.
            // So we create a variable to act as our Observable producer
            val observableProducer = database.collection(KEY_POIS)
                    .addSnapshotListener(EventListener<QuerySnapshot> { querySnapshot, e ->

                        // This is the Lambda expression used as the response to a Firestore snapshot

                        // Check to see if an error occurred.
                        if (e != null) {
                            Log.w(TAG, " Listener failed with error: ", e)
                            emitter.onError(e)
                            return@EventListener
                        }

                        // Check to see if we have content in the querySnapshot
                        if (!querySnapshot!!.isEmpty) {

                            // We need some code to convert the querySnapshot into a list of Pois
                            val results = mutableListOf<PoiRepository>()

                            for (document in querySnapshot.documents) {
                                val sentPOI = document.toObject(FireStorePoiResponse::class.java)
                                results.add(sentPOI!!.mapToRepository())
                            }

                            // Now we pass the results to the observable stream
                            emitter.onNext(results)
                        } else {
                            Log.d(TAG, " QuerySnapshot is empty")

                        }
                    })
            emitter.setCancellable { observableProducer.remove() }
        }
    }
}