package com.loc8r.seattleexplorer.domain.interfaces

import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Observable

interface DomainRepository {

    fun getPois(): Observable<List<PoiDomain>>
}