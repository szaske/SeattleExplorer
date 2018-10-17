/**
 * The Viewmodel connects the Domain to the view.  Some of the items currently handled by this viewmodel
 *
 * 1. Acts as the subscriber to the PoiData RxJava Single
 * 2. Converts the Single to LiveData
 * 3. Hosts the poiData LiveData stream, making it available to our fragment partner
 * 3. Converts (via Mapper) Domain models to Presentation models
 *
 */

package com.loc8r.seattleexplorer.presentation.poiList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.presentation.utils.PoiMapper
import com.loc8r.seattleexplorer.presentation.utils.Resource
import com.loc8r.seattleexplorer.presentation.utils.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

open class PoiListViewModel @Inject constructor(
        private val getPois: GetPois,
        private val mapper: PoiMapper
) : ViewModel() {

    private val poiData: MutableLiveData<Resource<List<PoiPresentation>>> = MutableLiveData()

    // initializer block, see: https://kotlinlang.org/docs/reference/classes.html
    init {
       fetchAllPois()
    }

    fun getAllPois(): LiveData<Resource<List<PoiPresentation>>> {
        return poiData
    }

    private fun fetchAllPois() {
        // This sets the default state of the screen
        poiData.postValue(Resource(ResourceState.LOADING,null,null))

        // This initializes the useCase which subscribes to the PoiList Single.
        // Params are optional
        getPois.execute(PoiSubscriber())
    }

    // this disposes of the single subscription as needed when the view is destroyed
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
            poiData.postValue(
                    Resource(ResourceState.SUCCESS,
                            data.map {
                                mapper.mapToPresentation(it)
                            },null))
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
            poiData.postValue(Resource(ResourceState.ERROR,null,e.localizedMessage))
            Log.e("Obervable error: ", e.localizedMessage )
        }
    }
}
