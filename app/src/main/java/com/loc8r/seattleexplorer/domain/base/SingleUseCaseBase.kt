package com.loc8r.seattleexplorer.domain.base

import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

// defined with T generics, so the class works with various Observable types
abstract class SingleUseCaseBase<T, in Params> constructor(
        // an abstraction for the schedule thread for each observable
        // required in the constructor
        private val observingThread: ObservingThread) {

    // A reference to the disposable observable.  Using plural naming since this is actually a set
    // and not a single item
    private val disposables = CompositeDisposable()

    // A method for creating a observable with parameters
    protected abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    // A method for executing the observable.  This does a bunch of stuff
    // 1. Creates the single
    // 2. selects the scheduler (see http://reactivex.io/documentation/operators/subscribeon.html)
    // 3. Creates a reference to the disposable SingleObservable
    open fun execute(singleObserver: DisposableSingleObserver<T>, params: Params? = null){
        val single = this.buildUseCaseSingle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(observingThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

    // Method that adds a disposable to the set of disposables.
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    // A method that clears out the disposable observers we've created.
    // This should be done when the viewmodel is destroyed.
    fun dispose() {
        disposables.dispose()
    }
}