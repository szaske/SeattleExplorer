
/**
 *  Within Clean Architecture, the Domain layer defines Use Cases
 *  which define operations which can be performed in our Android
 *  project. In this lesson, we’ll be creating some BASE classes
 *  which these Use Cases will inherit from to save us writing
 *  boilerplate code repeatedly in our Use Cases implementations.
 *
 *  see: https://stackoverflow.com/questions/42757924/what-is-the-difference-between-observable-completable-and-single-in-rxjava
 */

package com.loc8r.seattleexplorer.domain.base

import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

// defined with T generics, so the class works with various Observable types
abstract class CompletableUseCaseBase<in Params> constructor(
    // an abstraction for the schedule thread for each observable
    // required in the constructor
    private val observingThread: ObservingThreadInterface) {

    // A reference to the disposable observable, so we can dispose of it
    private val disposables = CompositeDisposable()

    // A method for creating a observable with parameters
    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    // A method for executing the observable.  This does a bunch of stuff
    // 1. Creates the observable
    // 2. selects the scheduler (see http://reactivex.io/documentation/operators/subscribeon.html)
    // 3. Creates a reference to the disposable Observable
    open fun execute(observer: DisposableCompletableObserver, params: Params? = null){
        val completable = this.buildUseCaseCompletable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(observingThread.scheduler)
        addDisposable(completable.subscribeWith(observer))
    }

    // Method that adds a disposable to the list of disposables.
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    // A method that clears out the disposable observers we've created.
    // This should be done when we want to unsubscribe from disposable observables.
    fun dispose() {
        disposables.dispose()
    }
}