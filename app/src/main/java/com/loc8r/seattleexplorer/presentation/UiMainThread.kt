/**
 *
 * The Domain layer is a Kotlin module and knows nothings of the Android framework. The
 * Domain layer is the main module that requests a PostExecutionThread.  This abstraction
 * allows me to keep all references to the Android framework out of the Domain layer/module.
 *
 */

package com.loc8r.seattleexplorer.presentation

import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UiMainThread @Inject constructor(): ObservingThreadInterface {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}