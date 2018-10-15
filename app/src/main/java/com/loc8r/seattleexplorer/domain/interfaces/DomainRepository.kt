package com.loc8r.seattleexplorer.domain.interfaces

import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Single

interface DomainRepository {

    fun getPois(): Single<List<PoiDomain>>
}