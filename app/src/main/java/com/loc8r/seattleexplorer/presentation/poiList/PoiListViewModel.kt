package com.loc8r.seattleexplorer.presentation.poiList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.presentation.utils.PoiMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

open class PoiListViewModel @Inject constructor(
        private val getPois: GetPois,
        private val mapper: PoiMapper
) : ViewModel() {

    private val poiData: MutableLiveData<List<PoiPresentation>> = MutableLiveData()

    // initializer block, see: https://kotlinlang.org/docs/reference/classes.html
    init {
       fetchAllPois()
    }

    fun getAllPois(): LiveData<List<PoiPresentation>> {
        return poiData
    }

    private fun fetchAllPois() {
        // This initializes the useCase and creates the observer.
        // Params are optional
        getPois.execute(PoiSubscriber())
    }

    // this disposes of the observable subscriptions as needed when the view is destroyed
    override fun onCleared() {
        getPois.dispose()
        super.onCleared()
    }

    // my solution for converting a Single to Livedata.
    // I also considered ReactiveStreams support for LiveData, but wasn't sure that was any easier

    inner class PoiSubscriber: DisposableSingleObserver<List<PoiDomain>>(){
        /**
         * Notifies the SingleObserver with a single item and that the [Single] has finished sending
         * push-based notifications.
         *
         *
         * The [Single] will not call this method if it calls [.onError].
         *
         * @param t
         * the item emitted by the Single
         */
        override fun onSuccess(data: List<PoiDomain>) {
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
