package com.loc8r.seattleexplorer.remote

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.loc8r.seattleexplorer.remote.models.FireStorePoiResponse
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class FirestoreDataListener @Inject constructor(): EventListener<QuerySnapshot> {

    private var emitter: ObservableEmitter<List<PoiRepository>>? = null

    companion object {
        private val TAG = FirestoreDataListener::class.qualifiedName
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
        // This is the Lambda expression used as the response to a Firestore snapshot

        // Check to see if an error occurred.
        if (e != null) {
            Log.w(TAG, " Listener failed with error: ", e)
            emitter?.onError(e)
            return
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
            emitter?.onNext(results)
        } else {
            Log.d(TAG, " QuerySnapshot is empty")

        }
    }

    operator fun invoke(emitter: ObservableEmitter<List<PoiRepository>>): FirestoreDataListener {
        this.emitter = emitter
        return this
    }
}