/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 *
 * Stuff that should go here:
 *
 * Data that needs to survive the Activity and Fragment lifecycle
 *
 * Caution: A ViewModel must never reference a view, Lifecycle, or any class that may hold a
 * reference to the activity context.
 */

package com.loc8r.seattleexplorer.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import com.loc8r.seattleexplorer.domain.GetCollections
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.RefreshAll
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.CollectionPresentation
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.presentation.utils.PresentationMapper
import com.loc8r.seattleexplorer.presentation.utils.Resource
import com.loc8r.seattleexplorer.presentation.utils.ResourceState
import io.reactivex.Observable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import javax.inject.Inject

open class SharedViewModel @Inject constructor(
        private val authService: AuthInterface,
        private val getCollections: GetCollections,
        private val getPois: GetPois,
        private val refreshAll: RefreshAll,
        private val mapper: PresentationMapper
): ViewModel() {

    private val poiData: MutableLiveData<Resource<List<PoiPresentation>>> = MutableLiveData()
    private val colData: MutableLiveData<Resource<List<CollectionPresentation>>> = MutableLiveData()

    fun isUserAuthenticated(): Boolean {
        return authService.getUser() != null
    }

    fun getUserEmail(): String{
        return authService.getUser()?.email ?: ""
    }

    fun signInWithEmail(email: String, password: String, onResult: (Task<AuthResult>) -> Unit) {
        return authService.signIn(email,password,onResult)
    }

    fun signOut(onResult: () -> Unit) {
        return authService.signOut(onResult)
    }

    fun getAllCollections(): LiveData<Resource<List<CollectionPresentation>>> {
        return colData
    }

    fun refreshLocalCacheData() {
        refreshAll.execute(RefreshAllSubscriber())
    }

    fun fetchAllCollections() {
        // This sets the default state of the screen
        colData.postValue(Resource(ResourceState.LOADING,null,null))

        // This initializes the useCase which subscribes to the PoiList Single.
        // Params are optional
        getCollections.execute(ColSubscriber())
    }

    // this disposes subscriptions as needed when the view is destroyed
    // TODO Find a way to determine which cases have been started and only dispose those
    override fun onCleared() {
        getCollections.dispose()
        getPois.dispose()
        refreshAll.dispose()
        super.onCleared()
    }

    fun getAllPois(): LiveData<Resource<List<PoiPresentation>>> {
        return poiData
    }

    fun fetchAllPois() {
        // This sets the default state of the screen
        poiData.postValue(Resource(ResourceState.LOADING,null,null))

        // This initializes the useCase which subscribes to the PoiList Single.
        // Params are optional
        getPois.execute(PoiSubscriber())
    }


    // my solution for converting a Single to Livedata.
    // I also considered ReactiveStreams support for LiveData, but wasn't sure that was any easier

    inner class PoiSubscriber: DisposableObserver<List<PoiDomain>>(){
        /**
         * Notifies the Observer that the [Observable] has finished sending push-based notifications.
         * The [Observable] will not call this method if it calls [.onError].
         */
        override fun onComplete() {
            Timber.i("Poi subscription completed")
        }

        /**
         * Provides the Observer with a new List of Poi to observe. The [Observable] may call this
         * method 0 or more times. The `Observable` will not call this method again after it calls
         * either [.onComplete] or [.onError].  It also maps the results to a Presentation layer
         * Poi model type
         *
         * @param data  the item emitted by the Observable
         */
        override fun onNext(data: List<PoiDomain>) {
            poiData.postValue(
                    Resource(ResourceState.SUCCESS,
                            data.map {
                                mapper.mapPoiToPresentation(it)
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
            Timber.e("""Observable error: ${e.localizedMessage}""")
        }
    }

    // my solution for converting a Single to Livedata.
    // I also considered ReactiveStreams support for LiveData, but wasn't sure that was any easier

    inner class ColSubscriber: DisposableObserver<List<CollectionDomain>>(){
        /**
         * Notifies the Observer that the [Observable] has finished sending push-based notifications.
         * The [Observable] will not call this method if it calls [.onError].
         */
        override fun onComplete() {
            Timber.i( "Collection subscription completed")
        }

        /**
         * Provides the Observer with a new List of Poi to observe. The [Observable] may call this
         * method 0 or more times. The `Observable` will not call this method again after it calls
         * either [.onComplete] or [.onError].  It also maps the results to a Presentation layer
         * Poi model type
         *
         * @param data  the item emitted by the Observable
         */
        override fun onNext(data: List<CollectionDomain>) {
            colData.postValue(
                    Resource(ResourceState.SUCCESS,
                            data.map {
                                mapper.mapColToPresentation(it)
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
            colData.postValue(Resource(ResourceState.ERROR,null,e.localizedMessage))
            Timber.e("Observable error ${e.localizedMessage}")
        }
    }

    inner class RefreshAllSubscriber: DisposableCompletableObserver() {
        /**
         * Called once the deferred computation completes normally.
         */
        override fun onComplete() {
            Timber.i("Completable completed")        }

        /**
         * Called once if the deferred computation 'throws' an exception.
         * @param e the exception, not null.
         */
        override fun onError(e: Throwable) {
            Timber.e("Completable error ${e.localizedMessage}")        }

    }

}
