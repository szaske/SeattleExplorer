package com.loc8r.seattleexplorer.domain.interfaces

import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import io.reactivex.Observable

interface ExplorerRepository {

    fun getPois(): Observable<List<Poi_Domain>>
}