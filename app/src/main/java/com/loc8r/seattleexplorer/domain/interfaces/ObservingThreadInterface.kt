package com.loc8r.seattleexplorer.domain.interfaces

import io.reactivex.Scheduler

interface ObservingThreadInterface {
    val scheduler: Scheduler
}