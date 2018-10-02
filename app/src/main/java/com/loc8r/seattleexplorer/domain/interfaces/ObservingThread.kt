package com.loc8r.seattleexplorer.domain.interfaces

import io.reactivex.Scheduler

interface ObservingThread {
    val scheduler: Scheduler
}