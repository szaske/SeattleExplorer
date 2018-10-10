package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Observable

interface ExplorerRemote {
    fun getPois(): Observable<List<PoiRepository>>
}