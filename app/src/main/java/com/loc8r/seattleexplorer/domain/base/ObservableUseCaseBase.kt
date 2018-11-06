package com.loc8r.seattleexplorer.domain.base

import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

// defined with T generics, so the class works with various Observable types
abstract class ObservableUseCaseBase<T, in Params> constructor(
        // an abstraction for the schedule thread for each observable
        // required in the constructor
        private val observingThread: ObservingThreadInterface) {

    // A reference to the disposable observable.  Using plural naming since this is actually a set
    // and not a single item
    private val disposables = CompositeDisposable()

    // A method for creating a observable with parameters
    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    // A method for executing the observable.  This does a bunch of stuff
    // 1. Creates the observable
    // 2. selects the scheduler (see http://reactivex.io/documentation/operators/subscribeon.html)
    // 3. Creates a reference to the disposable Observable
    open fun execute(singleObserver: DisposableObserver<T>, params: Params? = null){
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(observingThread.scheduler)
        addDisposable(observable.subscribeWith(singleObserver))
    }

    // Method that adds a disposable to the set of disposables.
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    // A method that clears out the disposable observers we've created.
    // This should be done when the viewModel is destroyed.
    fun dispose() {
        disposables.dispose()
    }
}