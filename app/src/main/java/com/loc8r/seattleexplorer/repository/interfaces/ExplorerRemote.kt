package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface ExplorerRemote {
    fun getPois(): Single<List<PoiRepository>>
}