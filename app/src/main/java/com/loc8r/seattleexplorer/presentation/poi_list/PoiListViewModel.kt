package com.loc8r.seattleexplorer.presentation.poi_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import com.loc8r.seattleexplorer.presentation.models.Poi_Presentation
import com.loc8r.seattleexplorer.presentation.utils.PoiMapper
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

open class PoiListViewModel @Inject constructor(
        private val getPois: GetPois,
        private val mapper: PoiMapper
) : ViewModel() {

    private val poiData: MutableLiveData<List<Poi_Presentation>> = MutableLiveData()

    // initializer block, see: https://kotlinlang.org/docs/reference/classes.html
    init {
       fetchAllPois()
    }

    fun getAllPois(): LiveData<List<Poi_Presentation>> {
        return poiData
    }

    fun fetchAllPois() {
        // This initializes the useCase and creates the observer.
        // Params are optional
        getPois.execute(PoiSubscriber())
    }

    // this disposes of the observable subscriptions as needed when the view is destroyed
    override fun onCleared() {
        getPois.dispose()
        super.onCleared()
    }

    // my solution for converting Observables to Livedata.
    // I also considered ReactiveStreams support for LiveData, but wasn't sure that was any easier

    inner class PoiSubscriber: DisposableObserver<List<Poi_Domain>>(){
        /**
         * Notifies the Observer that the [Observable] has finished sending push-based notifications.
         * The [Observable] will not call this method if it calls [.onError].
         */
        override fun onComplete() {
            Log.i("poiSubscriber: ", "completed")
        }

        /**
         * Provides the Observer with a new List of Poi to observe. The [Observable] may call this
         * method 0 or more times. The `Observable` will not call this method again after it calls
         * either [.onComplete] or [.onError].  It also maps the results to a Presentation layer
         * Poi model type
         *
         * @param data  the item emitted by the Observable
         */
        override fun onNext(data: List<Poi_Domain>) {
            poiData.postValue(data.map {
                mapper.mapToPresentation(it)
            })
        }

        /**
         * Notifies the Observer that the [Observable] has experienced an error condition.
         * If the [Observable] calls this method, it will not thereafter call [.onNext] or
         * [.onComplete].
         *
         * @param e
         * the exception encountered by the Observable
         */
        override fun onError(e: Throwable) {
            Log.e("Obervable error: ", e.localizedMessage )
        }
    }
}
